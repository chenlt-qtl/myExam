package org.exam.use;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import org.exam.ExcelToH2Importer;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarInfoSheetListener extends AnalysisEventListener<Map<Integer, String>> {
    private TableInfo tableInfo;
    private List<String> headers; // 处理后的表头（去重）
    // 常量定义
    private static final int MAX_VARCHAR_LENGTH = 500;
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("^\\d{6}$");

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile(
            "(?<!\\d)(?:(?:\\d{4}年(0?[1-9]|1[0-2])月(0?[1-9]|[12]\\d|3[01])日)|(?:\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01]))|(?:\\d{4}/(0?[1-9]|1[0-2])/(0?[1-9]|[12]\\d|3[01]))|(?:(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01])-\\d{4})|(?:(0?[1-9]|[12]\\d|3[01])/(0?[1-9]|1[0-2])/\\d{4})|(?:\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])))?" +
                    "(?:\\s+|T)?(?:(?:(2[0-3]|0?[0-9]|1[0-9]):(0?[0-5]?[0-9])(?::(0?[0-5]?[0-9]))?)(?:\\.\\d{1,3})?(?:\\s?[AP]M)?)?(?!\\d)");


    private static final int BATCH_SIZE = 10000; // 批量插入大小，可根据内存调整

    // 列类型字符串常量（替代枚举）
    public static final String TYPE_VARCHAR = "VARCHAR(" + MAX_VARCHAR_LENGTH + ")";
    public static final String TYPE_DATE = "DATE";
    public static final String TYPE_TIME = "TIME";
    public static final String TYPE_DATETIME = "TIMESTAMP";

    private final Map<String, TableInfo> tableInfoMap = new HashMap<>(); // 记录已处理的表名
    // 多线程相关变量
    private final ExecutorService executorService; // 线程池

    private final List<SheetDto> sheetDtoList = new ArrayList<>();//记录最终结果


    // 批量插入相关
    private final List<List<String>> currentBatch = new ArrayList<>(BATCH_SIZE); // 当前批次数据
    private final JdbcTemplate jdbcTemplate;
    private String insertSql = "";
    private Long start;



    private List<CompletableFuture> futures = new ArrayList<>();

    public CarInfoSheetListener(JdbcTemplate jdbcTemplate,ThreadPoolExecutor threadPoolExecutor) {
        this.jdbcTemplate  = jdbcTemplate;
        // 初始化线程池
        this.executorService = threadPoolExecutor;
        start = System.currentTimeMillis();
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
        initInsertSql();

        System.out.printf("=====表 %s 创建完成，共%d个字段%n", tableInfo.getTableName(), headers.size());

    }

    @Override
    public void invoke(Map<Integer, String> rowData, AnalysisContext context) {

        if (tableInfo == null) return;

        // 处理数据（截取、空值、格式转换）
        List<String> processedRow = processDataRow(rowData);

        // 线程安全地添加数据到当前批次
        synchronized (currentBatch) {
            currentBatch.add(processedRow);
            tableInfo.incrementDataCount();

            // 达到批次大小则提交任务
            if (currentBatch.size() >= BATCH_SIZE) {
                List<List<String>> batchToInsert = new ArrayList<>(currentBatch);
                futures.add(CompletableFuture.runAsync(new InsertTask(batchToInsert,jdbcTemplate,insertSql), executorService));
                currentBatch.clear();
            }
        }

    }

    // 所有数据处理完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        // 处理剩余数据
        synchronized (currentBatch) {
            if (!currentBatch.isEmpty()) {
                //executorService.submit(new InsertTask(new ArrayList<>(currentBatch),jdbcTemplate,insertSql));
                futures.add(CompletableFuture.runAsync(new InsertTask(new ArrayList<>(currentBatch),jdbcTemplate,insertSql), executorService));
                currentBatch.clear();
                System.out.printf("插入结束：共%d行数据%n", tableInfo.getDataCount());
            }
        }

        // 等待所有任务完成
        CompletableFuture<Void> allTask = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        // 阻塞直到全部完成（无需结果）
        allTask.join();
        //清除临时线程内容
        futures.clear();

        // 根据数据修改表结构
        if (tableInfo.hasData()) {
            alterTableStructure(tableInfo);
        }

        String finalDdl = generateFinalDdl(tableInfo);
        SheetDto sheetDto = new SheetDto();
        sheetDto.setTableName(tableInfo.getTableName());
        sheetDto.setSheetName(tableInfo.getSheetName());
        sheetDto.setDdl(finalDdl);
        sheetDtoList.add(sheetDto);
        System.out.println("耗时"+(System.currentTimeMillis()-start)/1000+"s");
    }

    public List<SheetDto> getResult() {
        return sheetDtoList;
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
        return new TableInfo(tableName, sheetName, columns);
    }

    // 检查表是否已在数据库中存在
    private boolean tableExists(String tableName) {
        try {
            // 从JdbcTemplate获取连接元数据
            return jdbcTemplate.getDataSource().getConnection().getMetaData()
                    .getTables(null, null, tableName, new String[]{"TABLE"})
                    .next();
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

        try {
            jdbcTemplate.execute(ddl.toString());
            tableInfo.setCreateDdl(ddl.toString());
        } catch (Exception e) {
            throw new RuntimeException("创建表失败: " + ddl, e);
        }
    }

    // 转义表名/列名（支持中文和特殊字符）
    private String escapeName(String name) {
        String newName =  "\"" + name.replace("\"", "\"\"").replaceAll("\n","_") + "\"";

        // 限制长度
        if (newName.length() > 40) {
            newName = newName.substring(0, 39);
        }
        return newName;
    }

    // 初始化批量插入语句
    private void initInsertSql() {
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

            insertSql = sql.toString();
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

            // 新增：移除数字中的逗号（处理千分位格式）
            // 判断是否可能为数字（包含数字和逗号）
            if (value.contains(",") && value.matches("^[0-9,]+(\\.[0-9]+)?$")) {
                value = value.replaceAll(",", "");
            }

            // 截取超长字符（超过500）
            if (value.length() > MAX_VARCHAR_LENGTH) {
                value = value.substring(0, MAX_VARCHAR_LENGTH);
            }

            // 日期格式标准化
            boolean matches = DATE_TIME_PATTERN.matcher(value).matches();
            if(matches) {
                value = standardizeDate(value);
            }
            rowValues.add(value);
            tableInfo.addDataSample(i, value);
        }
        return rowValues;
    }

    // 日期格式标准化（转换为数据库兼容格式）
    private String standardizeDate(String value) {
        if (value == null || value.isEmpty()) return value;

        // 尝试多种常见日期格式
        SimpleDateFormat[] parsers = {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
                new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"),

                new SimpleDateFormat("yyyy年MM月dd日 HH:mm"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                new SimpleDateFormat("yyyy/MM/dd HH:mm"),

                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("MM-dd-yyyy"),
                new SimpleDateFormat("dd/MM/yyyy"),

                new SimpleDateFormat("yyyy年MM月dd日"),
                new SimpleDateFormat("HH:mm:ss")
        };

        for (SimpleDateFormat parser : parsers) {
            parser.setLenient(false);
            try {
                Date date = parser.parse(value);
                // 输出格式：日期->yyyy-MM-dd，时间->HH:mm:ss，日期时间->yyyy-MM-dd HH:mm:ss
                if (parser.toPattern().contains(" ")) {
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

                try {
                    System.out.printf("字段 %s 类型修改为: %s%n", columnName, targetType);
                    jdbcTemplate.execute(alterSql); // JdbcTemplate执行ALTER
                    tableInfo.setColumnType(i, targetType);
                    System.out.printf("修改成功%n");
                } catch (Exception e) {
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