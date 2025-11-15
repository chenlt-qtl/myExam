package org.exam;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ExcelToH2Importer1 {
    // H2数据库连接配置（支持中文表名/字段名）
    private static final String H2_URL = "jdbc:h2:./excel_h2_db;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    // 性能与内存控制参数
    private static final int BATCH_SIZE = 5000;         // 批量插入大小
    private static final int SXSSF_WINDOW_SIZE = 1000;  // 内存中缓存的Excel行数
    private static final int MAX_VARCHAR_LENGTH = 500;  // 最大字符串长度

    // 日期格式匹配（支持多种常见格式，含中文）
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("dd-MM-yyyy"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("yyyyMMdd"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
            new SimpleDateFormat("HH:mm:ss"),
            new SimpleDateFormat("yyyy年MM月dd日"),
            new SimpleDateFormat("MM月dd日yyyy年")
    );

    // 年月格式匹配（19xx01-20xx12，如199701、202412）
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("^(19|20)\\d{2}(0[1-9]|1[0-2])$");

    // 存储建表DDL语句（表名 -> DDL列表）
    private static final Map<String, List<String>> tableDDLs = new LinkedHashMap<>();

    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.err.println("请传入Excel文件路径，例如：java ExcelToH2Importer D:/数据.xlsx");
//            return;
//        }
        String excelPath = "C:\\Users\\Administrator\\Desktop\\车档信息-TEST.xlsx";

        try (Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD)) {
            conn.setAutoCommit(false);
            boolean hasValidData = importExcel(excelPath, conn);

            if (hasValidData) {
                conn.commit();
                System.out.println("\n===== 建表DDL =====");
                printAllDDLs();
            } else {
                conn.rollback();
                System.out.println("Excel文件为空或所有Sheet仅含表头，未执行导入");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入Excel文件到H2数据库
     * @return 是否存在有效数据
     */
    private static boolean importExcel(String excelPath, Connection conn) throws Exception {
        try (InputStream is = new FileInputStream(excelPath)) {
            Workbook workbook = createWorkbook(is, excelPath);
            boolean hasValidData = false;

            // 检查Excel是否为空（无Sheet）
            if (workbook.getNumberOfSheets() == 0) {
                System.out.println("Excel文件为空（无Sheet）");
                return false;
            }

            // 处理每个Sheet
            for (int sheetIdx = 0; sheetIdx < workbook.getNumberOfSheets(); sheetIdx++) {
                Sheet sheet = workbook.getSheetAt(sheetIdx);
                String rawSheetName = sheet.getSheetName().trim();
                String sheetName = rawSheetName.isEmpty() ? "工作表_" + (sheetIdx + 1) : rawSheetName;

                // 检查Sheet是否只有表头（无有效数据行）
                if (isSheetOnlyHeader(sheet)) {
                    System.out.println("Sheet [" + sheetName + "] 仅含表头，跳过处理");
                    continue;
                }

                // 生成唯一表名（处理重名）
                String tableName = getUniqueTableName(sheetName, conn);
                System.out.println("处理Sheet：" + rawSheetName + " -> 表名：" + tableName);

                // 处理当前Sheet
                processSheet(sheet, tableName, conn);
                hasValidData = true;
            }

            // 清理SXSSF临时文件
            if (workbook instanceof SXSSFWorkbook) {
                ((SXSSFWorkbook) workbook).dispose();
            }

            return hasValidData;
        }
    }

    /**
     * 检查Sheet是否只有表头（无实际数据行）
     */
    private static boolean isSheetOnlyHeader(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return true; // 无表头视为空Sheet
        }

        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            return true; // 只有表头行（索引0）
        }

        // 检查所有数据行是否均为空
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null && !isRowEmpty(row)) {
                return false; // 存在非空数据行
            }
        }
        return true; // 所有数据行均为空
    }

    /**
     * 检查行是否为空（所有单元格均为空）
     */
    private static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据文件类型创建Workbook（xlsx用流式处理）
     */
    private static Workbook createWorkbook(InputStream is, String filePath) throws IOException {
        if (filePath.endsWith(".xlsx")) {
            return new SXSSFWorkbook(new XSSFWorkbook(is), SXSSF_WINDOW_SIZE, true);
        } else if (filePath.endsWith(".xls")) {
            return new HSSFWorkbook(is);
        } else {
            throw new IllegalArgumentException("不支持的文件格式，仅支持.xls和.xlsx");
        }
    }

    /**
     * 获取唯一表名（存在则加序号，如"表名_1"）
     */
    private static String getUniqueTableName(String baseName, Connection conn) throws SQLException {
        if (!tableExists(baseName, conn)) {
            return baseName;
        }

        int suffix = 1;
        while (true) {
            String candidate = baseName + "_" + suffix;
            if (!tableExists(candidate, conn)) {
                return candidate;
            }
            suffix++;
        }
    }

    /**
     * 检查表是否存在（查询H2系统表）
     */
    private static boolean tableExists(String tableName, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * 处理单个Sheet：建表→插入数据→修改结构→记录DDL
     */
    private static void processSheet(Sheet sheet, String tableName, Connection conn) throws Exception {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            System.out.println("跳过空Sheet：" + tableName);
            return;
        }

        // 解析列名（处理重复列名，加序号）
        List<String> columnNames = resolveColumnNames(headerRow);
        int columnCount = columnNames.size();

        // 初始化当前表的DDL存储
        tableDDLs.put(tableName, new ArrayList<>());

        // 1. 创建初始表（所有列VARCHAR(500)）
        String createDDL = createInitialTable(conn, tableName, columnNames);
        tableDDLs.get(tableName).add(createDDL);

        // 2. 批量插入数据并收集列类型信息
        List<ColumnTypeInfo> columnTypeInfos = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            columnTypeInfos.add(new ColumnTypeInfo());
        }
        batchInsertData(sheet, tableName, columnNames, columnTypeInfos, conn);

        // 3. 根据数据类型修改表结构
        List<String> alterDDLs = alterTableStructure(conn, tableName, columnNames, columnTypeInfos);
        tableDDLs.get(tableName).addAll(alterDDLs);
    }

    /**
     * 解析表头为列名（支持中文，处理重复列名）
     */
    private static List<String> resolveColumnNames(Row headerRow) {
        List<String> columnNames = new ArrayList<>();
        Map<String, Integer> nameCounter = new HashMap<>();

        for (int cellIdx = 0; cellIdx < headerRow.getPhysicalNumberOfCells(); cellIdx++) {
            Cell cell = headerRow.getCell(cellIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String rawName = getCellValue(cell).trim();  // 调用getCellValue方法

            // 空列名处理
            String baseName = rawName.isEmpty() ? "列_" + (cellIdx + 1) : rawName;

            // 处理重复列名（加_1、_2...）
            int count = nameCounter.getOrDefault(baseName, 0) + 1;
            nameCounter.put(baseName, count);
            String columnName = (count > 1) ? baseName + "_" + count : baseName;

            columnNames.add(columnName);
        }
        return columnNames;
    }

    /**
     * 获取单元格的字符串值（补充完整的getCellValue方法）
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日期类型转换为字符串
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                } else {
                    // 数字类型避免科学计数法
                    double value = cell.getNumericCellValue();
                    if (value == (long) value) {
                        return String.valueOf((long) value);
                    } else {
                        return String.valueOf(value);
                    }
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                // 公式单元格取计算结果
                try {
                    if (cell.getCachedFormulaResultType() == CellType.STRING) {
                        return cell.getStringCellValue();
                    } else if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                        } else {
                            double value = cell.getNumericCellValue();
                            return value == (long) value ? String.valueOf((long) value) : String.valueOf(value);
                        }
                    } else if (cell.getCachedFormulaResultType() == CellType.BOOLEAN) {
                        return String.valueOf(cell.getBooleanCellValue());
                    } else {
                        return "";
                    }
                } catch (Exception e) {
                    return "";
                }

            case BLANK:
                return "";

            default:
                return "";
        }
    }

    /**
     * 创建初始表（所有列均为VARCHAR(500)）
     */
    private static String createInitialTable(Connection conn, String tableName, List<String> columnNames) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            StringBuilder createSql = new StringBuilder("CREATE TABLE \"" + tableName + "\" (");

            for (int i = 0; i < columnNames.size(); i++) {
                if (i > 0) {
                    createSql.append(", ");
                }
                createSql.append("\"").append(columnNames.get(i)).append("\" VARCHAR(").append(MAX_VARCHAR_LENGTH).append(")");
            }

            createSql.append(")");
            stmt.execute(createSql.toString());
            return createSql.toString();
        }
    }

    /**
     * 批量插入数据并收集列类型信息
     */
    private static void batchInsertData(Sheet sheet, String tableName, List<String> columnNames,
                                        List<ColumnTypeInfo> typeInfos, Connection conn) throws SQLException {
        int columnCount = columnNames.size();
        String insertSql = buildInsertSql(tableName, columnNames);

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            int batchIndex = 0;

            // 从第二行开始处理数据（跳过表头）
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null || isRowEmpty(row)) {
                    continue; // 跳过空行
                }

                for (int colIdx = 0; colIdx < columnCount; colIdx++) {
                    Cell cell = row.getCell(colIdx, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = processCellValue(cell);

                    // 处理#为空值
                    if ("#".equals(cellValue.trim())) {
                        pstmt.setNull(colIdx + 1, Types.VARCHAR);
                        typeInfos.get(colIdx).processValue(null);
                    } else {
                        pstmt.setString(colIdx + 1, cellValue);
                        typeInfos.get(colIdx).processValue(cellValue);
                    }
                }

                pstmt.addBatch();
                batchIndex++;

                // 达到批量大小则执行
                if (batchIndex >= BATCH_SIZE) {
                    pstmt.executeBatch();
                    batchIndex = 0;
                }
            }

            // 处理剩余数据
            if (batchIndex > 0) {
                pstmt.executeBatch();
            }
        }
    }

    /**
     * 构建插入SQL语句
     */
    private static String buildInsertSql(String tableName, List<String> columnNames) {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < columnNames.size(); i++) {
            if (i > 0) {
                columns.append(", ");
                placeholders.append(", ");
            }
            columns.append("\"").append(columnNames.get(i)).append("\"");
            placeholders.append("?");
        }

        return "INSERT INTO \"" + tableName + "\" (" + columns + ") VALUES (" + placeholders + ")";
    }

    /**
     * 处理单元格值（计算公式结果、截取超长字符串）
     */
    private static String processCellValue(Cell cell) {
        // 直接调用getCellValue获取基础值，再进行后续处理
        String value = getCellValue(cell);

        // 截取超过500字符的内容
        if (value.length() > MAX_VARCHAR_LENGTH) {
            value = value.substring(0, MAX_VARCHAR_LENGTH);
        }
        return value;
    }

    /**
     * 格式化数字（避免科学计数法，去除末尾多余0）
     */
    private static String formatNumber(double num) {
        if (num == (long) num) {
            return String.valueOf((long) num); // 整数
        } else {
            return String.valueOf(num).replaceAll("0+$", "").replaceAll("\\.$", ""); // 小数
        }
    }

    /**
     * 根据数据类型修改表结构，返回ALTER语句列表
     */
    private static List<String> alterTableStructure(Connection conn, String tableName,
                                                    List<String> columnNames, List<ColumnTypeInfo> typeInfos) throws SQLException {
        List<String> alterDDLs = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                ColumnTypeInfo typeInfo = typeInfos.get(i);
                String targetType = determineColumnType(typeInfo);

                if ("VARCHAR(500)".equals(targetType)) {
                    continue; // 类型不变，无需修改
                }

                // 构建ALTER语句
                String alterSql = String.format(
                        "ALTER TABLE \"%s\" ALTER COLUMN \"%s\" SET DATA TYPE %s",
                        tableName, columnName, targetType
                );
                stmt.execute(alterSql);
                alterDDLs.add(alterSql);
            }
        }
        return alterDDLs;
    }

    /**
     * 根据列数据信息确定目标数据类型
     */
    private static String determineColumnType(ColumnTypeInfo typeInfo) {
        // 情况1：全是日期/时间
        if (typeInfo.isAllDates()) {
            return typeInfo.hasTime() ? "TIMESTAMP" : "DATE";
        }

        // 情况2：全是数字且不含年月格式
        if (typeInfo.isAllNumbers() && !typeInfo.hasYearMonth()) {
            if (typeInfo.getMaxFractionDigits() == 0) {
                // 整数类型（根据最大整数位数选择INT/BIGINT）
                return typeInfo.getMaxIntegerDigits() <= 9 ? "INT" : "BIGINT";
            } else {
                // 小数类型（DECIMAL(总长度, 小数位数)）
                int totalDigits = typeInfo.getMaxIntegerDigits() + typeInfo.getMaxFractionDigits();
                return String.format("DECIMAL(%d, %d)",
                        Math.min(totalDigits, 38),  // H2最大精度38
                        Math.min(typeInfo.getMaxFractionDigits(), 38)
                );
            }
        }

        // 其他情况保持VARCHAR(500)
        return "VARCHAR(500)";
    }

    /**
     * 打印所有表的DDL语句
     */
    private static void printAllDDLs() {
        for (Map.Entry<String, List<String>> entry : tableDDLs.entrySet()) {
            System.out.println("\n表名：" + entry.getKey());
            for (String ddl : entry.getValue()) {
                System.out.println(ddl + ";");
            }
        }
    }

    /**
     * 列类型信息收集器（记录列数据特征用于类型判断）
     */
    private static class ColumnTypeInfo {
        private int nonNullCount = 0;         // 非空值数量
        private int dateCount = 0;            // 日期值数量
        private int timeCount = 0;            // 含时间的日期数量
        private int numberCount = 0;          // 数字值数量
        private int yearMonthCount = 0;       // 年月格式值数量
        private int maxIntegerDigits = 0;     // 最大整数位数
        private int maxFractionDigits = 0;    // 最大小数位数

        /**
         * 处理单个值，更新类型信息
         */
        public void processValue(String value) {
            if (value == null || value.trim().isEmpty()) {
                return;
            }

            nonNullCount++;
            String trimmedValue = value.trim();

            // 检查是否为日期
            if (isDate(trimmedValue)) {
                dateCount++;
                // 检查是否包含时间信息
                if (trimmedValue.contains(" ") || trimmedValue.contains(":")) {
                    timeCount++;
                }
                return;
            }

            // 检查是否为年月格式（如202401）
            if (YEAR_MONTH_PATTERN.matcher(trimmedValue).matches()) {
                yearMonthCount++;
                return;
            }

            // 检查是否为数字
            if (isNumber(trimmedValue)) {
                numberCount++;
                // 计算整数和小数位数
                String[] parts = trimmedValue.split("\\.");
                int integerDigits = parts[0].length();
                int fractionDigits = parts.length > 1 ? parts[1].length() : 0;

                maxIntegerDigits = Math.max(maxIntegerDigits, integerDigits);
                maxFractionDigits = Math.max(maxFractionDigits, fractionDigits);
            }
        }

        /**
         * 判断是否为日期
         */
        private boolean isDate(String value) {
            for (SimpleDateFormat sdf : DATE_FORMATS) {
                try {
                    sdf.parse(value);
                    return true;
                } catch (ParseException e) {
                    // 尝试下一种格式
                }
            }
            return false;
        }

        /**
         * 判断是否为数字
         */
        private boolean isNumber(String value) {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Getter方法
        public boolean isAllDates() {
            return nonNullCount > 0 && dateCount == nonNullCount;
        }

        public boolean hasTime() {
            return timeCount > 0;
        }

        public boolean isAllNumbers() {
            return nonNullCount > 0 && numberCount == nonNullCount;
        }

        public boolean hasYearMonth() {
            return yearMonthCount > 0;
        }

        public int getMaxIntegerDigits() {
            return maxIntegerDigits;
        }

        public int getMaxFractionDigits() {
            return maxFractionDigits;
        }
    }
}