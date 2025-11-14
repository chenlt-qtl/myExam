package org.exam;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStrings;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ExcelToH2Importer {
    private static final String H2_URL = "jdbc:h2:./excelDB;AUTO_SERVER=TRUE";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";
    private static final int BATCH_SIZE = 1000;
    private static final int SAMPLE_ROWS = 100; // 用于推断列类型的样本行数
    // 日期格式支持列表（可根据实际需求扩展）
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("MM-dd-yyyy"),
            new SimpleDateFormat("MM/dd/yyyy")
    );

    private String tableName;
    private List<String> columnNames = new ArrayList<>();
    private List<String> columnTypes = new ArrayList<>();
    // 记录每列小数位数（仅对DECIMAL类型有效）
    private List<Integer> decimalScales = new ArrayList<>();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int rowCount = 0;

    public static void main(String[] args) {


        try {
            ExcelToH2Importer importer = new ExcelToH2Importer();
            importer.importExcel("C:\\Users\\Administrator\\Desktop\\滴滴对账单202508.xlsx", "test_01");
            System.out.println("导入完成! 共导入 " + importer.rowCount + " 行数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importExcel(String excelFilePath, String tableName) throws Exception {
        this.tableName = tableName;
        this.connection = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
        connection.setAutoCommit(false);

        try {
            analyzeExcelStructure(excelFilePath);
            createTable();
            importData(excelFilePath);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    private void analyzeExcelStructure(String excelFilePath) throws Exception {
        try (OPCPackage opcPackage = OPCPackage.open(new File(excelFilePath))) {
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStrings sharedStrings = xssfReader.getSharedStringsTable();

            XMLReader parser = XMLReaderFactory.createXMLReader();
            StructureAnalysisHandler handler = new StructureAnalysisHandler(sharedStrings);
            parser.setContentHandler(handler);

            InputStream sheetInputStream = xssfReader.getSheetsData().next();
            InputSource inputSource = new InputSource(sheetInputStream);
            parser.parse(inputSource);
            sheetInputStream.close();

            this.columnNames = handler.getColumnNames();
            determineColumnTypesAndScales(handler.getSampleData());
        }
    }

    /**
     * 改进的列类型推断：
     * 1. 区分整数（BIGINT）和小数（DECIMAL）
     * 2. 计算小数列的最大精度和小数位数
     * 3. 日期类型使用DATE而非TIMESTAMP
     */
    private void determineColumnTypesAndScales(List<List<String>> sampleData) {
        columnTypes.clear();
        decimalScales.clear();
        if (sampleData.isEmpty() || sampleData.get(0).isEmpty()) {
            return;
        }

        int columnCount = sampleData.get(0).size();
        for (int i = 0; i < columnCount; i++) {
            boolean isInteger = true;
            boolean isDecimal = true;
            boolean isDate = true;
            int maxScale = 0;  // 最大小数位数
            int maxPrecision = 0; // 最大总位数

            for (List<String> row : sampleData) {
                if (i >= row.size()) continue;
                String value = row.get(i);
                if (value == null || value.trim().isEmpty()) continue;

                // 检查是否为数字（整数或小数）
                try {
                    BigDecimal bd = new BigDecimal(value);
                    int precision = bd.precision();
                    int scale = bd.scale();

                    maxPrecision = Math.max(maxPrecision, precision);
                    maxScale = Math.max(maxScale, scale);

                    if (scale != 0) {
                        isInteger = false; // 有小数位，不是整数
                    }
                } catch (NumberFormatException e) {
                    isInteger = false;
                    isDecimal = false;
                }

                // 检查是否为日期
                if (!isDate(value)) {
                    isDate = false;
                }

                // 提前退出判断
                if (!isInteger && !isDecimal && !isDate) {
                    break;
                }
            }

            // 确定最终类型
            if (isInteger) {
                columnTypes.add("BIGINT");
                decimalScales.add(0); // 整数无小数位
            } else if (isDecimal) {
                // 确保精度和小数位合理（至少1位整数位）
                int precision = Math.max(maxPrecision, maxScale + 1);
                int scale = maxScale;
                columnTypes.add(String.format("DECIMAL(%d, %d)", precision, scale));
                decimalScales.add(scale);
            } else if (isDate) {
                columnTypes.add("DATE");
                decimalScales.add(0); // 日期无需小数位
            } else {
                columnTypes.add("VARCHAR(255)");
                decimalScales.add(0);
            }
        }
    }

    /**
     * 检查字符串是否符合任何支持的日期格式
     */
    private boolean isDate(String value) {
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            sdf.setLenient(false); // 严格模式，避免错误解析
            try {
                sdf.parse(value);
                return true;
            } catch (ParseException e) {
                continue;
            }
        }
        return false;
    }

    private void createTable() throws SQLException {
        // 删除已有表
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS " + tableName);
        }

        // 创建新表（使用推断的类型和小数位）
        StringBuilder createSql = new StringBuilder("CREATE TABLE " + tableName + " ( ");
        for (int i = 0; i < columnNames.size(); i++) {
            String columnName = "COL_" + (i + 1);
            if (columnNames.get(i) != null && !columnNames.get(i).trim().isEmpty()) {
                columnName = columnNames.get(i);
            }
            createSql.append(columnName).append(" ").append(columnTypes.get(i));
            if (i < columnNames.size() - 1) {
                createSql.append(", ");
            }
        }
        createSql.append(" )");

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql.toString());
        }
    }

    private void importData(String excelFilePath) throws Exception {
        // 构建插入SQL
        StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for (int i = 0; i < columnNames.size(); i++) {
            insertSql.append("?");
            if (i < columnNames.size() - 1) {
                insertSql.append(", ");
            }
        }
        insertSql.append(")");
        preparedStatement = connection.prepareStatement(insertSql.toString());

        // 流式读取并插入数据
        try (OPCPackage opcPackage = OPCPackage.open(new File(excelFilePath))) {
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStrings sharedStrings = xssfReader.getSharedStringsTable();

            XMLReader parser = XMLReaderFactory.createXMLReader();
            DataImportHandler handler = new DataImportHandler(sharedStrings);
            parser.setContentHandler(handler);

            InputStream sheetInputStream = xssfReader.getSheetsData().next();
            InputSource inputSource = new InputSource(sheetInputStream);
            parser.parse(inputSource);
            sheetInputStream.close();

            // 处理剩余的批次数据
            if (rowCount % BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
            }
        }
    }

    // 工具方法：从SharedStrings获取字符串
    private String getSharedString(SharedStrings sharedStrings, String indexStr) {
        if (indexStr == null || indexStr.trim().isEmpty()) {
            return "";
        }
        try {
            int index = Integer.parseInt(indexStr);
            if (index >= 0 && index < sharedStrings.getCount()) {
                return sharedStrings.getItemAt(index).getString();
            }
            return indexStr;
        } catch (NumberFormatException e) {
            return indexStr;
        }
    }

    // 分析Excel结构的处理器
    private class StructureAnalysisHandler extends DefaultHandler {
        private SharedStrings sharedStrings;
        private String lastContents;
        private boolean nextIsString;
        private List<List<String>> sampleData = new ArrayList<>();
        private List<String> currentRow = new ArrayList<>();
        private boolean isHeaderRow = true;
        private List<String> columnNames = new ArrayList<>();

        public StructureAnalysisHandler(SharedStrings sharedStrings) {
            this.sharedStrings = sharedStrings;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) {
            if (name.equals("c")) {
                String cellType = attributes.getValue("t");
                nextIsString = "s".equals(cellType);
            }
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) {
            if (nextIsString) {
                lastContents = getSharedString(sharedStrings, lastContents);
                nextIsString = false;
            }

            if (name.equals("v")) {
                currentRow.add(lastContents);
            } else if (name.equals("row")) {
                if (isHeaderRow) {
                    columnNames.addAll(currentRow);
                    isHeaderRow = false;
                } else if (sampleData.size() < SAMPLE_ROWS) {
                    sampleData.add(new ArrayList<>(currentRow));
                }
                currentRow.clear();
            }
        }

        public void characters(char[] ch, int start, int length) {
            lastContents += new String(ch, start, length);
        }

        public List<String> getColumnNames() {
            return columnNames;
        }

        public List<List<String>> getSampleData() {
            return sampleData;
        }
    }

    // 导入数据的处理器
    private class DataImportHandler extends DefaultHandler {
        private SharedStrings sharedStrings;
        private String lastContents;
        private boolean nextIsString;
        private List<String> currentRow = new ArrayList<>();
        private boolean isHeaderRow = true;

        public DataImportHandler(SharedStrings sharedStrings) {
            this.sharedStrings = sharedStrings;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) {
            if (name.equals("c")) {
                String cellType = attributes.getValue("t");
                nextIsString = "s".equals(cellType);
            }
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException {
            if (nextIsString) {
                lastContents = getSharedString(sharedStrings, lastContents);
                nextIsString = false;
            }

            if (name.equals("v")) {
                currentRow.add(lastContents);
            } else if (name.equals("row")) {
                if (isHeaderRow) {
                    isHeaderRow = false;
                } else {
                    try {
                        addRowToBatch(currentRow);
                    } catch (SQLException e) {
                        throw new SAXException(e);
                    }
                }
                currentRow.clear();
            }
        }

        public void characters(char[] ch, int start, int length) {
            lastContents += new String(ch, start, length);
        }

        private void addRowToBatch(List<String> row) throws SQLException {
            rowCount++;

            for (int i = 0; i < row.size() && i < columnTypes.size(); i++) {
                String value = row.get(i);
                String type = columnTypes.get(i);

                if (value == null || value.trim().isEmpty()) {
                    preparedStatement.setNull(i + 1, Types.NULL);
                    continue;
                }

                // 根据列类型设置参数（重点改进数字和日期处理）
                if (type.startsWith("DECIMAL")) {
                    try {
                        BigDecimal bd = new BigDecimal(value);
                        // 保留指定的小数位数（四舍五入）
                        bd = bd.setScale(decimalScales.get(i), BigDecimal.ROUND_HALF_UP);
                        preparedStatement.setBigDecimal(i + 1, bd);
                    } catch (NumberFormatException e) {
                        preparedStatement.setString(i + 1, value);
                    }
                } else if (type.equals("BIGINT")) {
                    try {
                        preparedStatement.setLong(i + 1, Long.parseLong(value));
                    } catch (NumberFormatException e) {
                        preparedStatement.setString(i + 1, value);
                    }
                } else if (type.equals("DATE")) {
                    try {
                        Date date = parseDate(value);
                        preparedStatement.setDate(i + 1, new java.sql.Date(date.getTime()));
                    } catch (ParseException e) {
                        preparedStatement.setString(i + 1, value);
                    }
                } else {
                    preparedStatement.setString(i + 1, value);
                }
            }

            preparedStatement.addBatch();

            if (rowCount % BATCH_SIZE == 0) {
                preparedStatement.executeBatch();
                connection.commit();
                System.out.println("已导入 " + rowCount + " 行数据");
            }
        }

        /**
         * 解析日期字符串为Date对象（使用支持的格式列表）
         */
        private Date parseDate(String value) throws ParseException {
            for (SimpleDateFormat sdf : DATE_FORMATS) {
                sdf.setLenient(false);
                try {
                    return sdf.parse(value);
                } catch (ParseException e) {
                    continue;
                }
            }
            throw new ParseException("不支持的日期格式: " + value, 0);
        }
    }
}