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
    private static final int SAMPLE_ROWS = 100;
    // 日期格式支持列表
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("MM-dd-yyyy"),
            new SimpleDateFormat("MM/dd/yyyy"),
            new SimpleDateFormat("yyyy年MM月dd日")
    );

    private String tableName;
    private List<String> columnNames = new ArrayList<>();
    private List<String> columnTypes = new ArrayList<>();
    private List<Integer> decimalScales = new ArrayList<>();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int rowCount = 0;
    private int columnCount = 0;
    // 新增：记录已修改为VARCHAR的列索引，避免重复修改
    private Set<Integer> alteredColumns = new HashSet<>();

    public static void main(String[] args) {
        try {
            ExcelToH2Importer importer = new ExcelToH2Importer();
            importer.importExcel("C:\\Users\\Administrator\\Desktop\\车档信息-TEST.xlsx", "test_01");
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
            this.columnCount = columnNames.size();
            if (this.columnCount == 0) {
                this.columnCount = handler.getMaxCellCount();
                for (int i = 0; i < this.columnCount; i++) {
                    columnNames.add("COL_" + (i + 1));
                }
            }
            determineColumnTypesAndScales(handler.getSampleData());
        }
    }

    private void determineColumnTypesAndScales(List<List<String>> sampleData) {
        columnTypes.clear();
        decimalScales.clear();
        if (sampleData.isEmpty() || columnCount == 0) {
            return;
        }

        for (int i = 0; i < columnCount; i++) {
            boolean isInteger = true;
            boolean isDecimal = true;
            boolean isDate = true;
            int maxScale = 0;
            int maxPrecision = 0;

            for (List<String> row : sampleData) {
                if (i >= row.size()) {
                    continue;
                }
                String value = row.get(i);
                if (value == null || value.trim().isEmpty()) {
                    continue;
                }

                // 检查数字类型
                try {
                    BigDecimal bd = new BigDecimal(value);
                    int precision = bd.precision();
                    int scale = bd.scale();

                    maxPrecision = Math.max(maxPrecision, precision);
                    maxScale = Math.max(maxScale, scale);

                    if (scale != 0) {
                        isInteger = false;
                    }
                } catch (NumberFormatException e) {
                    isInteger = false;
                    isDecimal = false;
                }

                // 检查日期类型
                if (!isDate(value)) {
                    isDate = false;
                }

                if (!isInteger && !isDecimal && !isDate) {
                    break;
                }
            }

            // 确定列类型
            if (isInteger) {
                columnTypes.add("BIGINT");
                decimalScales.add(0);
            } else if (isDecimal) {
                int precision = Math.max(maxPrecision, maxScale + 1);
                int scale = maxScale;
                columnTypes.add(String.format("DECIMAL(%d, %d)", precision + scale, scale));
                decimalScales.add(scale);
            } else if (isDate) {
                columnTypes.add("DATE");
                decimalScales.add(0);
            } else {
                columnTypes.add("VARCHAR(255)");
                decimalScales.add(0);
            }
        }
    }

    private boolean isDate(String value) {
        for (SimpleDateFormat sdf : DATE_FORMATS) {
            sdf.setLenient(false);
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
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS " + tableName);
        }

        StringBuilder createSql = new StringBuilder("CREATE TABLE " + tableName + " ( ");
        for (int i = 0; i < columnCount; i++) {
            String columnName = columnNames.get(i);
            columnName = columnName.replaceAll("[^a-zA-Z0-9_\\u4e00-\\u9fa5]", "_");
            createSql.append("`").append(columnName).append("` ").append(columnTypes.get(i));
            if (i < columnCount - 1) {
                createSql.append(", ");
            }
        }
        createSql.append(" )");

        System.out.println("create sql:" + createSql);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql.toString());
        }
    }

    private void importData(String excelFilePath) throws Exception {
        recreatePreparedStatement();

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

            if (rowCount % BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
            }
        }
    }

    // 新增：重新创建PreparedStatement（表结构变更时使用）
    private void recreatePreparedStatement() throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }

        StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
        for (int i = 0; i < columnCount; i++) {
            insertSql.append("?");
            if (i < columnCount - 1) {
                insertSql.append(", ");
            }
        }
        insertSql.append(")");
        preparedStatement = connection.prepareStatement(insertSql.toString());
    }

    // 新增：处理类型不匹配，将列转为VARCHAR
    private void handleTypeMismatch(int columnIndex) throws SQLException {
        if (alteredColumns.contains(columnIndex)) {
            return;
        }

        // 修改列类型为VARCHAR
        String columnName = columnNames.get(columnIndex);
        columnName = columnName.replaceAll("[^a-zA-Z0-9_\\u4e00-\\u9fa5]", "_");
        String alterSql = String.format("ALTER TABLE %s ALTER COLUMN `%s` SET DATA TYPE VARCHAR(255)",
                tableName, columnName);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(alterSql);
        }

        // 更新列类型记录
        columnTypes.set(columnIndex, "VARCHAR(255)");
        alteredColumns.add(columnIndex);

        // 重新创建PreparedStatement
        recreatePreparedStatement();
    }

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

    private class StructureAnalysisHandler extends DefaultHandler {
        private SharedStrings sharedStrings;
        private String lastContents;
        private boolean nextIsString;
        private List<List<String>> sampleData = new ArrayList<>();
        private List<String> currentRow = new ArrayList<>();
        private boolean isHeaderRow = true;
        private List<String> columnNames = new ArrayList<>();
        private int maxCellCount = 0;

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

            if (name.equals("v") || name.equals("t")) {
                currentRow.add(lastContents);
            } else if (name.equals("row")) {
                maxCellCount = Math.max(maxCellCount, currentRow.size());

                if (isHeaderRow) {
                    columnNames.addAll(currentRow);
                    isHeaderRow = false;
                } else if (sampleData.size() < SAMPLE_ROWS) {
                    List<String> fixedRow = new ArrayList<>(currentRow);
                    while (fixedRow.size() < columnCount) {
                        fixedRow.add("");
                    }
                    sampleData.add(fixedRow);
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

        public int getMaxCellCount() {
            return maxCellCount;
        }
    }

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

            if (name.equals("v") || name.equals("t")) {
                currentRow.add(lastContents);
            } else if (name.equals("row")) {
                if (isHeaderRow) {
                    isHeaderRow = false;
                } else {
                    try {
                        List<String> fixedRow = new ArrayList<>(currentRow);
                        while (fixedRow.size() < columnCount) {
                            fixedRow.add("");
                        }
                        if (fixedRow.size() > columnCount) {
                            fixedRow = fixedRow.subList(0, columnCount);
                        }
                        addRowToBatch(fixedRow);
                    } catch (SQLException e) {
                        throw new SAXException("第" + (rowCount + 1) + "行数据导入失败: " + e.getMessage(), e);
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

            for (int i = 0; i < columnCount; i++) {
                String value = row.get(i);
                String type = columnTypes.get(i);

                if (value == null || value.trim().isEmpty()) {
                    preparedStatement.setNull(i + 1, Types.NULL);
                    continue;
                }

                // 类型转换逻辑，增加类型不匹配处理
                try {
                    if (type.startsWith("DECIMAL")) {
                        BigDecimal bd = new BigDecimal(value);
                        bd = bd.setScale(decimalScales.get(i), BigDecimal.ROUND_HALF_UP);
                        preparedStatement.setBigDecimal(i + 1, bd);
                    } else if (type.equals("BIGINT")) {
                        preparedStatement.setLong(i + 1, Long.parseLong(value));
                    } else if (type.equals("DATE")) {
                        Date date = parseDate(value);
                        preparedStatement.setDate(i + 1, new java.sql.Date(date.getTime()));
                    } else {
                        preparedStatement.setString(i + 1, value);
                    }
                } catch (Exception e) {
                    // 转换失败，将列转为VARCHAR后重试
                    handleTypeMismatch(i);
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