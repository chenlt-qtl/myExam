package org.exam.use;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 表信息封装类
@Data
public class TableInfo {
        private final String tableName;
        private final String sheetName;
        private final List<String> columnNames;
        private final List<List<String>> dataSamples; // 每列的数据样本
        private final List<String> columnTypes;      // 每列的最终类型
        private int dataCount = 0;                   // 总数据行数
        private String createDdl;                    // 创建表的初始DDL

        public TableInfo(String tableName,String sheetName, List<String> columnNames) {
            this.tableName = tableName;
            this.sheetName = sheetName;
            this.columnNames = columnNames;
            this.dataSamples = new ArrayList<>();
            this.columnTypes = new ArrayList<>();
            for (int i = 0; i < columnNames.size(); i++) {
                dataSamples.add(new ArrayList<>());
                columnTypes.add(CarInfoSheetListener.TYPE_VARCHAR); // 初始为VARCHAR
            }
        }


        public void addDataSample(int columnIndex, String value) {
            if (columnIndex < dataSamples.size()) {
                dataSamples.get(columnIndex).add(value);
            }
        }
        public List<String> getDataSamples(int columnIndex) {
            return columnIndex < dataSamples.size() ? dataSamples.get(columnIndex) : Collections.emptyList();
        }
        public void incrementDataCount() { dataCount++; }

        public boolean hasData() { return dataCount > 0; }

        public void setColumnType(int columnIndex, String type) {
            if (columnIndex < columnTypes.size()) {
                columnTypes.set(columnIndex, type);
            }
        }
    }
