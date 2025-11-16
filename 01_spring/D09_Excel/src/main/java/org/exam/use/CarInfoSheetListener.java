package org.exam.use;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import org.exam.ExcelToH2Importer;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarInfoSheetListener extends AnalysisEventListener<Map<Integer, String>> {
    // 存储结果：key=Sheet名，value=该Sheet的所有行数据（每行是表头-值的Map）
    private final Map<String, List<Map<Integer, String>>> sheetDataMap = new HashMap<>();
    private List<Map<Integer, String>> currentSheetData;
    private String currentSheetName;
    private TableInfo tableInfo;
    private List<String> headers; // 处理后的表头（去重）
    // 常量定义
    private static final String H2_URL = "jdbc:h2:mem:excelimport;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";
    private static final int MAX_VARCHAR_LENGTH = 500;
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("^\\d{6}$");
    private static final int BATCH_SIZE = 5000; // 批量插入大小，可根据内存调整

    // 列类型字符串常量（替代枚举）
    public static final String TYPE_VARCHAR = "VARCHAR(" + MAX_VARCHAR_LENGTH + ")";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_TIME = "TIME";
    public static final String TYPE_DATETIME = "TIMESTAMP";

    private final Map<String, TableInfo> tableInfoMap = new HashMap<>(); // 记录已处理的表名

    private final Map<String,String> finalDdls = new HashMap<>();//记录最终DDL


    // 批量插入相关
    private List<List<String>> batchData = new ArrayList<>(BATCH_SIZE);
    private PreparedStatement batchStmt;
    private Connection connection;
    private final JdbcDataSource dataSource;

    public CarInfoSheetListener(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 处理表头（第一行）
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        String sheetName = context.readSheetHolder().getSheetName();

        headers = new ArrayList<>(headMap.size());
        // 处理表头：去重（重名列加序号）
        processHeaders(headMap);

        // 创建表信息（表名去重）
        tableInfo = createTableInfo(sheetName, headers);
        tableInfoMap.put(tableInfo.getTableName(), tableInfo);

        // 创建表（初始字段全为VARCHAR(500)）
        createTable(tableInfo);

        // 初始化批量插入语句
        initBatchStatement();

        System.out.printf("=====表 %s 创建完成，共%d个字段%n", tableInfo.getTableName(), headers.size());

    }

    @Override
    public void invoke(Map<Integer, String> rowData, AnalysisContext context) {

        if (tableInfo == null) return;

        String sheetName = context.readSheetHolder().getSheetName();

        // 处理数据（截取、空值、格式转换）
        List<String> processedRow = processDataRow(rowData);
        batchData.add(processedRow);
        tableInfo.incrementDataCount();

        if(currentSheetName == null) {
            currentSheetName = sheetName;
            currentSheetData = new ArrayList<>();
        }

        // 达到批量大小则执行插入
        if (batchData.size() >= BATCH_SIZE) {
            executeBatchInsert();
        }

        // 切换Sheet时保存上一个Sheet数据
        if (currentSheetName == null || !currentSheetName.equals(sheetName)) {
            if (currentSheetName != null) {
                sheetDataMap.put(currentSheetName, currentSheetData);
            }
            currentSheetName = sheetName;
        }

        // 保存当前行数据（表头-值的映射）
        currentSheetData.add(new HashMap<>(rowData));
    }

    // 所有数据处理完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余数据
        if (!batchData.isEmpty()) {
            executeBatchInsert();
            System.out.printf("插入结束：共%d行数据%n", tableInfo.getDataCount());
        }

        // 根据数据修改表结构
        if (tableInfo.hasData()) {
            alterTableStructure(tableInfo);
        }

        String finalDdl = generateFinalDdl(tableInfo);
        finalDdls.put(tableInfo.getTableName(), finalDdl);

        if (currentSheetName != null) {
            sheetDataMap.put(currentSheetName, currentSheetData);
        }

        // 关闭资源
        closeResources();
    }

    public Map<String, List<Map<Integer, String>>> getSheetDataMap() {
        return sheetDataMap;
    }

    public Map<String, String> getFinalDdls() {
        return finalDdls;
    }

    // 处理表头去重（重名列加序号）
    private void processHeaders(Map<Integer, String> headMap) {
        System.out.println("开始处理表头");
        Map<String, Integer> columnCounter = new HashMap<>();
        // 按列索引排序，保证表头顺序
        List<Map.Entry<Integer, String>> sortedHeaders = new ArrayList<>(headMap.entrySet());
        sortedHeaders.sort(Comparator.comparing(Map.Entry::getKey));

        for (Map.Entry<Integer, String> entry : sortedHeaders) {
            //列名为空则已经到最后
            if(StringUtils.isBlank(entry.getValue())){
                break;
            }
            String columnName = entry.getValue().trim();

            // 处理重名
            columnCounter.put(columnName, columnCounter.getOrDefault(columnName, 0) + 1);
            int count = columnCounter.get(columnName);
            if (count > 1) {
                headers.add(columnName + "_" + count);
            } else {
                headers.add(columnName);
            }
        }
        System.out.println("表头处理完成");
    }

    // 创建表信息（处理表名重复）
    private TableInfo createTableInfo(String sheetName, List<String> columns) {
        String baseName = sheetName.trim().isEmpty() ? "工作表" : sheetName.trim();
        String tableName = baseName;

        // 检查表名是否已存在（内存+数据库）
        int suffix = 1;
        while (tableInfoMap.containsKey(tableName) || tableExists(tableName)) {
            tableName = baseName + "_" + suffix++;
        }
        return new TableInfo(tableName, columns);
    }

    // 检查表是否已在数据库中存在
    private boolean tableExists(String tableName) {
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    // 创建表（初始字段全为VARCHAR(500)）
    private void createTable(TableInfo tableInfo) {
        StringBuilder ddl = new StringBuilder("CREATE TABLE ");
        ddl.append(escapeName(tableInfo.getTableName())).append(" (");

        List<String> columns = tableInfo.getColumnNames();
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) ddl.append(", ");
            ddl.append(escapeName(columns.get(i))).append(" ").append(TYPE_VARCHAR);
        }
        ddl.append(")");

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(ddl.toString());
            tableInfo.setCreateDdl(ddl.toString());
        } catch (SQLException e) {
            throw new RuntimeException("创建表失败: " + ddl, e);
        }
    }

    // 转义表名/列名（支持中文和特殊字符）
    private String escapeName(String name) {
        return "\"" + name.replace("\"", "\"\"").replaceAll("\n","_") + "\"";
    }

    // 初始化批量插入语句
    private void initBatchStatement() {
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false); // 关闭自动提交，提升批量插入效率

            // 构建INSERT SQL
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(escapeName(tableInfo.getTableName())).append(" (");
            for (int i = 0; i < headers.size(); i++) {
                if (i > 0) sql.append(", ");
                sql.append(escapeName(headers.get(i)));
            }
            sql.append(") VALUES (");
            for (int i = 0; i < headers.size(); i++) {
                if (i > 0) sql.append(", ");
                sql.append("?");
            }
            sql.append(")");

            batchStmt = connection.prepareStatement(sql.toString());
        } catch (SQLException e) {
            throw new RuntimeException("初始化批量插入语句失败", e);
        }
    }

    // 处理单行数据
    private List<String> processDataRow(Map<Integer, String> dataMap) {
        List<String> rowValues = new ArrayList<>(headers.size());
        for (int i = 0; i < headers.size(); i++) {
            // EasyExcel自动计算公式结果，直接获取
            String value = dataMap.getOrDefault(i, "");

            // #视为空值
            if ("#".equals(value)|| StringUtils.isBlank(value)) {
                rowValues.add(null);
                tableInfo.addDataSample(i, null);
                continue;
            }

            value = value.trim();

            // 截取超长字符（超过500）
            if (value.length() > MAX_VARCHAR_LENGTH) {
                value = value.substring(0, MAX_VARCHAR_LENGTH);
            }

            // 日期格式标准化
            String standardizedValue = standardizeDate(value);
            rowValues.add(standardizedValue);
            tableInfo.addDataSample(i, standardizedValue);
        }
        return rowValues;
    }

    // 日期格式标准化（转换为数据库兼容格式）
    private String standardizeDate(String value) {
        if (value == null || value.isEmpty()) return value;

        // 尝试多种常见日期格式
        SimpleDateFormat[] parsers = {
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("MM-dd-yyyy"),
                new SimpleDateFormat("dd/MM/yyyy"),
                new SimpleDateFormat("yyyy年MM月dd日"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
                new SimpleDateFormat("HH:mm:ss")
        };

        for (SimpleDateFormat parser : parsers) {
            try {
                Date date = parser.parse(value);
                // 输出格式：日期->yyyy-MM-dd，时间->HH:mm:ss，日期时间->yyyy-MM-dd HH:mm:ss
                if (parser.toPattern().contains("HH:mm:ss")) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                } else if (value.contains(":")) {
                    return new SimpleDateFormat("HH:mm:ss").format(date);
                } else {
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                }
            } catch (ParseException e) {
                // 尝试下一种格式
            }
        }
        return value; // 非日期格式，原样返回
    }

    // 执行批量插入
    private void executeBatchInsert() {
        try {
            for (List<String> row : batchData) {
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    if (value == null) {
                        batchStmt.setNull(i + 1, Types.VARCHAR);
                    } else {
                        batchStmt.setString(i + 1, value);
                    }
                }
                batchStmt.addBatch();
            }
            batchStmt.executeBatch();
            connection.commit();
            batchData.clear();
            System.out.printf("已插入 %d 行数据...%n", tableInfo.getDataCount());
        } catch (SQLException e) {
            try {
                connection.rollback(); // 失败回滚
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("批量插入失败", e);
        }
    }

    // 关闭数据库资源
    private void closeResources() {
        try {
            if (batchStmt != null) batchStmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据数据类型修改表结构
    private void alterTableStructure(TableInfo tableInfo) {
        System.out.println("修改类型开始====================");
        String tableName = tableInfo.getTableName();
        List<String> columns = tableInfo.getColumnNames();

        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String targetType = determineColumnType(tableInfo.getDataSamples(i));

            // 类型不同时才修改
            if (!TYPE_VARCHAR.equals(targetType)) {
                String alterSql = String.format("ALTER TABLE %s ALTER COLUMN %s %s",
                        escapeName(tableName),
                        escapeName(columnName),
                        targetType);

                try (Connection conn = dataSource.getConnection();
                     Statement stmt = conn.createStatement()) {
                    System.out.printf("字段 %s 类型修改为: %s%n", columnName, targetType);
                    stmt.execute(alterSql);
                    tableInfo.setColumnType(i, targetType);
                    System.out.printf("修改成功%n");
                } catch (SQLException e) {
                    // 修改失败则保持VARCHAR
                    tableInfo.setColumnType(i, TYPE_VARCHAR);
                    System.out.printf("字段 %s 类型修改失败，保持VARCHAR%n", columnName);
                }
            } else {
                tableInfo.setColumnType(i, TYPE_VARCHAR);
            }
        }
        System.out.println("修改类型完成====================");
    }

    // 推断列的数据类型
    private String determineColumnType(List<String> samples) {
        // 过滤空值样本
        List<String> validSamples = new ArrayList<>();
        for (String s : samples) {
            if (s != null && !s.isEmpty()) {
                validSamples.add(s);
            }
        }
        if (validSamples.isEmpty()) {
            return TYPE_VARCHAR; // 无有效数据，保持VARCHAR
        }

        // 1. 检查是否全为日期
        if (isAllDates(validSamples)) {
            boolean hasTime = validSamples.stream().anyMatch(s -> s.contains(":"));
            return hasTime ? TYPE_DATETIME : TYPE_DATE;
        }

        // 2. 检查是否全为时间
        if (isAllTimes(validSamples)) {
            return TYPE_TIME;
        }

        // 3. 检查是否为年月格式（6位数字且符合年月范围）
        if (isAllYearMonth(validSamples)) {
            return TYPE_VARCHAR;
        }

        // 4. 检查是否全为数字
        NumberType numberType = analyzeNumberType(validSamples);
        if (numberType != null) {
            return numberType.type == NumberType.INTEGER ? "INT" :
                    String.format("NUMERIC(%d,%d)", numberType.precision, numberType.scale);
        }

        // 默认为VARCHAR
        return TYPE_VARCHAR;
    }

    // 检查是否全为日期（yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss）
    private boolean isAllDates(List<String> samples) {
        for (String s : samples) {
            if (!s.matches("^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?$")) {
                return false;
            }
        }
        return true;
    }

    // 检查是否全为时间（HH:mm:ss）
    private boolean isAllTimes(List<String> samples) {
        for (String s : samples) {
            if (!s.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
                return false;
            }
        }
        return true;
    }

    // 检查是否全为年月格式（6位数字，符合1900-2100年，1-12月）
    private boolean isAllYearMonth(List<String> samples) {
        for (String s : samples) {
            Matcher matcher = YEAR_MONTH_PATTERN.matcher(s);
            if (!matcher.matches()) {
                return false;
            }
            try {
                int val = Integer.parseInt(s);
                int year = val / 100;
                int month = val % 100;
                if (year < 1900 || year > 2100 || month < 1 || month > 12) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    // 分析数字类型（整数/小数及精度）
    private NumberType analyzeNumberType(List<String> samples) {
        int maxIntegerDigits = 0;
        int maxFractionDigits = 0;

        for (String s : samples) {
            // 移除千分位逗号
            String numStr = s.replace(",", "");
            // 排除科学计数法
            if (numStr.contains("e") || numStr.contains("E")) {
                return null;
            }

            try {
                // 尝试解析为数字
                Double.parseDouble(numStr);
                // 拆分整数和小数部分
                String[] parts = numStr.split("\\.");
                int integerLen = parts[0].length();
                int fractionLen = parts.length > 1 ? parts[1].length() : 0;

                maxIntegerDigits = Math.max(maxIntegerDigits, integerLen);
                maxFractionDigits = Math.max(maxFractionDigits, fractionLen);
            } catch (NumberFormatException e) {
                return null; // 非数字
            }
        }

        // 所有样本均为数字
        return new NumberType(
                NumberType.DECIMAL,
                maxIntegerDigits + maxFractionDigits,
                maxFractionDigits
        );
    }

    // 生成最终表结构DDL
    private String generateFinalDdl(TableInfo tableInfo) {
        StringBuilder ddl = new StringBuilder("CREATE TABLE ");
        ddl.append(escapeName(tableInfo.getTableName())).append(" (");

        List<String> columns = tableInfo.getColumnNames();
        List<String> types = tableInfo.getColumnTypes();

        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) ddl.append(", ");
            ddl.append(escapeName(columns.get(i))).append(" ").append(types.get(i));
        }
        ddl.append(")");
        return ddl.toString();
    }
}