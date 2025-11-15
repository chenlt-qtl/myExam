package org.exam;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelToH2Importer {
    private static final String H2_URL = "jdbc:h2:mem:excelimport;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";
    private static final int MAX_VARCHAR_LENGTH = 500;
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("^([0-9]{4})(0[1-9]|1[0-2])$");

    private JdbcDataSource dataSource;
    private Map<String, TableInfo> tableInfoMap = new HashMap<>();

    public ExcelToH2Importer() {
        dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);
        dataSource.setUser(H2_USER);
        dataSource.setPassword(H2_PASSWORD);
    }

    public List<String> importExcel(String filePath) throws SQLException {
        // 1. 检查Excel是否为空或只有表头
        if (isExcelEmpty(filePath)) {
            return Collections.emptyList();
        }

        // 2. 读取所有sheet并处理
        List<String> ddlList = new ArrayList<>();
        EasyExcel.read(filePath,new SheetDiscoveryListener(ddlList))
                .headRowNumber(1)
                .doReadAll();

        System.out.println("最终DDL"+ddlList);
        return ddlList;
    }

    private boolean isExcelEmpty(String filePath) {
        ExcelEmptyChecker checker = new ExcelEmptyChecker();
        EasyExcel.read(filePath, checker).sheet().doRead();
        return checker.isAllSheetsEmpty();
    }

    private class SheetDiscoveryListener extends AnalysisEventListener<Map<Integer, String>> {
        private final List<String> ddlList;
        private String currentSheetName;
        private TableInfo currentTableInfo;
        private boolean isFirstRow = true;

        public SheetDiscoveryListener(List<String> ddlList) {
            this.ddlList = ddlList;
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

            currentSheetName = context.readSheetHolder().getSheetName();
            System.out.println("开始创建表"+currentSheetName);
            currentTableInfo = createTableInfo(currentSheetName, headMap);
            tableInfoMap.put(currentSheetName, currentTableInfo);

            // 创建表
            String createTableDdl = createTable(currentTableInfo);
            System.out.println("创建表结束"+createTableDdl);
            currentTableInfo.setCreateTableDdl(createTableDdl);
        }

        @Override
        public void invoke(Map<Integer, String> dataMap, AnalysisContext context) {
            if (isFirstRow) {
                System.out.println("开始插入数据");
                isFirstRow = false;
            }
            System.out.println("currentTableInfo:"+currentTableInfo==null);
            System.out.println("dataMap:"+dataMap==null);
            System.out.println(dataMap);
            // 处理并插入数据
            if(currentTableInfo!=null && dataMap.isEmpty()) {
                processAndInsertData(currentTableInfo, dataMap);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            if (currentTableInfo != null && currentTableInfo.hasData()) {
                System.out.println("开始修正表结构");
                // 修改表结构
                alterTableStructure(currentTableInfo);
                // 获取最终表结构的DDL
                String finalDdl = getFinalTableDdl(currentTableInfo);
                ddlList.add(finalDdl);
            }
            currentTableInfo = null;
            isFirstRow = true;
        }
    }

    private String getFinalTableDdl(TableInfo tableInfo) {
        StringBuilder ddl = new StringBuilder("CREATE TABLE ");
        ddl.append(escapeName(tableInfo.getTableName())).append(" (");

        List<String> columns = tableInfo.getColumnNames();
        List<String> columnTypes = tableInfo.getColumnTypes();

        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                ddl.append(", ");
            }
            ddl.append(escapeName(columns.get(i))).append(" ")
                    .append(columnTypes.get(i));
        }

        ddl.append(")");
        return ddl.toString();
    }

    private String escapeName(String name) {
        return "\"" + name.replace("\"", "\"\"") + "\"";
    }

    private TableInfo createTableInfo(String sheetName, Map<Integer, String> headMap) {
        // 处理表名重复
        String tableName = getUniqueTableName(sheetName);
        
        // 处理列名重复
        List<String> columnNames = new ArrayList<>();
        Map<String, Integer> columnCount = new HashMap<>();
        for (String column : headMap.values()) {
            if(StringUtils.isBlank(column)) {
                //如果列名为空，表示已到最后
                break;
            }
            String colName = column.trim();
            if (colName.isEmpty()) {
                colName = "COLUMN";
            }
            
            if (columnCount.containsKey(colName)) {
                int count = columnCount.get(colName) + 1;
                columnCount.put(colName, count);
                colName += "_" + count;
            } else {
                columnCount.put(colName, 1);
            }
            columnNames.add(colName);
        }
        
        return new TableInfo(tableName, columnNames);
    }

    private String getUniqueTableName(String sheetName) {
        String baseName = sheetName.trim();
        if (baseName.isEmpty()) {
            baseName = "SHEET";
        }
        
        if (!tableInfoMap.containsKey(baseName) && !tableExists(baseName)) {
            return baseName;
        }
        
        int suffix = 1;
        while (true) {
            String candidate = baseName + "_" + suffix;
            if (!tableInfoMap.containsKey(candidate) && !tableExists(candidate)) {
                return candidate;
            }
            suffix++;
        }
    }

    private boolean tableExists(String tableName) {
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private String createTable(TableInfo tableInfo) {
        StringBuilder ddl = new StringBuilder("CREATE TABLE ");
        ddl.append(escapeTableName(tableInfo.getTableName())).append(" (");
        
        List<String> columns = tableInfo.getColumnNames();
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                ddl.append(", ");
            }
            ddl.append(escapeColumnName(columns.get(i))).append(" VARCHAR(").append(MAX_VARCHAR_LENGTH).append(")");
        }
        
        ddl.append(")");
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(ddl.toString());
        } catch (SQLException e) {
            throw new RuntimeException("创建表失败: " + ddl, e);
        }
        
        return ddl.toString();
    }

    private void processAndInsertData(TableInfo tableInfo, Map<Integer, String> dataMap) {
        List<String> columns = tableInfo.getColumnNames();
        List<String> values = new ArrayList<>();
        
        for (int i = 0; i < columns.size(); i++) {
            String value = dataMap.getOrDefault(i, "");
            
            // 处理#为空值
            if ("#".equals(value)|| StringUtils.isBlank(value)) {
                values.add(null);
                tableInfo.addDataSample(i, null);
                continue;
            }

            value = value.trim();
            
            // 处理公式结果（EasyExcel默认会读取公式结果）
            // 截取超过长度的字符串
            if (value.length() > MAX_VARCHAR_LENGTH) {
                value = value.substring(0, MAX_VARCHAR_LENGTH);
            }

            //识别日期并转换为标准格式（暂存为字符串，后续修改类型时兼容）
            if (DateUtils.isDate(value)) {
                value = DateUtils.parseAndFormat(value);
            }
            
            values.add(value);
            tableInfo.addDataSample(i, value);
        }
        
        // 插入数据
        insertData(tableInfo.getTableName(), columns, values);
        tableInfo.incrementDataCount();
    }

    private void insertData(String tableName, List<String> columns, List<String> values) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(escapeTableName(tableName)).append(" (");
        
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(escapeColumnName(columns.get(i)));
        }
        
        sql.append(") VALUES (");
        
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        
        sql.append(")");
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                if (value == null) {
                    pstmt.setNull(i + 1, Types.VARCHAR);
                } else {
                    pstmt.setString(i + 1, value);
                }
            }
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入数据失败: " + sql, e);
        }
    }

    private String alterTableStructure(TableInfo tableInfo) {
        StringBuilder alterDdl = new StringBuilder();
        String tableName = tableInfo.getTableName();
        List<String> columns = tableInfo.getColumnNames();
        
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String type = determineColumnType(tableInfo.getDataSamples(i));
            
            if (type != VARCHAR) {
                String alterSql = String.format("ALTER TABLE %s ALTER COLUMN %s %s",
                        escapeTableName(tableName),
                        escapeColumnName(columnName),
                        type);
                
                try (Connection conn = dataSource.getConnection();
                     Statement stmt = conn.createStatement()) {
                    stmt.execute(alterSql);
                    tableInfo.setColumnType(i, type);
                    if (alterDdl.length() > 0) {
                        alterDdl.append("\n");
                    }
                    alterDdl.append(alterSql);
                } catch (SQLException e) {
                    tableInfo.setColumnType(i, VARCHAR);
                    throw new RuntimeException("修改表结构失败: " + alterSql, e);

                }
            }else {
                tableInfo.setColumnType(i, VARCHAR);
            }
        }
        
        return alterDdl.toString();
    }

    private String determineColumnType(List<String> samples) {
        // 检查是否全是日期/时间
        boolean allDates = true;
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("MM-dd-yyyy"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("HH:mm:ss")
        };
        
        for (String sample : samples) {
            if (sample == null || sample.isEmpty()) continue;
            
            boolean isDate = false;
            for (SimpleDateFormat format : dateFormats) {
                try {
                    format.parse(sample);
                    isDate = true;
                    break;
                } catch (ParseException e) {
                    // 不是该格式的日期
                }
            }
            
            if (!isDate) {
                allDates = false;
                break;
            }
        }
        
        if (allDates && !samples.isEmpty()) {
            return DATE;
        }
        
        // 检查是否全是数字
        boolean allNumbers = true;
        int maxIntegerDigits = 0;
        int maxFractionDigits = 0;
        
        for (String sample : samples) {
            if (sample == null || sample.isEmpty()) continue;
            
            // 检查是否是年月格式 (如202401)
            Matcher matcher = YEAR_MONTH_PATTERN.matcher(sample);
            if (matcher.matches()) {
                try {
                    int value = Integer.parseInt(sample);
                    int year = value / 100;
                    int month = value % 100;
                    if (year >= 1900 && year <= 2100 && month >= 1 && month <= 12) {
                        allNumbers = false;
                        break;
                    }
                } catch (NumberFormatException e) {
                    allNumbers = false;
                    break;
                }
            }
            
            try {
                // 检查是否是数字
                String numStr = sample.replaceAll(",", "");
                double num = Double.parseDouble(numStr);
                
                // 计算整数和小数位数
                String[] parts = numStr.split("\\.");
                int integerDigits = parts[0].length();
                int fractionDigits = parts.length > 1 ? parts[1].length() : 0;
                
                maxIntegerDigits = Math.max(maxIntegerDigits, integerDigits);
                maxFractionDigits = Math.max(maxFractionDigits, fractionDigits);
            } catch (NumberFormatException e) {
                allNumbers = false;
                break;
            }
        }
        
        if (allNumbers && !samples.isEmpty()) {
            return "NUMERIC(" + (maxIntegerDigits + maxFractionDigits) + "," + maxFractionDigits + ")";
        }
        
        // 默认为VARCHAR
        return VARCHAR;
    }

    private String escapeTableName(String tableName) {
        return "\"" + tableName.replace("\"", "\"\"") + "\"";
    }

    private String escapeColumnName(String columnName) {
        return "\"" + columnName.replace("\"", "\"\"") + "\"";
    }

    private static class ExcelEmptyChecker extends AnalysisEventListener<Map<Integer, String>> {
        private int sheetCount = 0;
        private int nonEmptySheetCount = 0;
        private boolean currentSheetHasData = false;
        private boolean isFirstRow = true;

        @Override
        public void invoke(Map<Integer, String> dataMap, AnalysisContext context) {
            if (isFirstRow) {
                isFirstRow = false;
                return; // 跳过表头
            }
            
            if (!currentSheetHasData) {
                for (String value : dataMap.values()) {
                    if (value != null && !value.trim().isEmpty() && !"#".equals(value.trim())) {
                        currentSheetHasData = true;
                        nonEmptySheetCount++;
                        break;
                    }
                }
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            sheetCount++;
            currentSheetHasData = false;
            isFirstRow = true;
        }

        public boolean isAllSheetsEmpty() {
            return sheetCount == 0 || nonEmptySheetCount == 0;
        }
    }

    public static class TableInfo {
        private final String tableName;
        private final List<String> columnNames;
        private final List<List<String>> dataSamples;
        private int dataCount = 0;
        private String createTableDdl;
        private final List<String> columnTypes;

        public TableInfo(String tableName, List<String> columnNames) {
            this.tableName = tableName;
            this.columnNames = columnNames;
            this.dataSamples = new ArrayList<>();
            this.columnTypes = new ArrayList<>();
            for (int i = 0; i < columnNames.size(); i++) {
                dataSamples.add(new ArrayList<>());
                columnTypes.add(VARCHAR);
            }
        }

        public String getTableName() {
            return tableName;
        }

        public List<String> getColumnNames() {
            return columnNames;
        }

        public void addDataSample(int columnIndex, String value) {
            if (columnIndex < dataSamples.size()) {
                dataSamples.get(columnIndex).add(value);
            }
        }

        public List<String> getDataSamples(int columnIndex) {
            if (columnIndex < dataSamples.size()) {
                return dataSamples.get(columnIndex);
            }
            return Collections.emptyList();
        }

        public void incrementDataCount() {
            dataCount++;
        }

        public boolean hasData() {
            return dataCount > 0;
        }

        public void setCreateTableDdl(String ddl) {
            this.createTableDdl = ddl;
        }

        public String getCreateTableDdl() {
            return createTableDdl;
        }

        public void setColumnType(int columnIndex, String type) {
            if (columnIndex < columnTypes.size()) {
                columnTypes.set(columnIndex, type);
            }
        }

        public List<String> getColumnTypes() {
            return columnTypes;
        }
    }

    private final static String VARCHAR = "VARCHAR(" + MAX_VARCHAR_LENGTH + ")";

    private final static String DATE = "DATE";

    private final static String TIME = "TIME";

    private final static String DATETIME = "DATETIME";

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            ExcelToH2Importer importer = new ExcelToH2Importer();
            List<String> ddls = importer.importExcel("C:\\Users\\Administrator\\Desktop\\滴滴对账单202508 (1).xlsx");

//            List<String> ddls = importer.importExcel("C:\\Users\\Administrator\\Desktop\\车档信息-TEST.xlsx");

            System.out.println("耗时:"+(System.currentTimeMillis()-start)/1000+"秒");
            System.out.println("生成的DDL语句:");
            for (String ddl : ddls) {
                System.out.println(ddl);
                System.out.println("------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}