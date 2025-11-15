package org.exam.base;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarInfoSheetListener extends AnalysisEventListener<Map<String, Object>> {
    // 存储结果：key=Sheet名，value=该Sheet的所有行数据（每行是表头-值的Map）
    private final Map<String, List<Map<String, Object>>> sheetDataMap = new HashMap<>();
    private List<Map<String, Object>> currentSheetData;
    private String currentSheetName;

    @Override
    public void invoke(Map<String, Object> rowData, AnalysisContext context) {
        String sheetName = context.readSheetHolder().getSheetName();

        // 切换Sheet时保存上一个Sheet数据
        if (currentSheetName == null || !currentSheetName.equals(sheetName)) {
            if (currentSheetName != null) {
                sheetDataMap.put(currentSheetName, currentSheetData);
            }
            currentSheetName = sheetName;
            currentSheetData = new ArrayList<>();
        }

        // 保存当前行数据（表头-值的映射）
        currentSheetData.add(new HashMap<>(rowData));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (currentSheetName != null) {
            sheetDataMap.put(currentSheetName, currentSheetData);
        }
    }

    public Map<String, List<Map<String, Object>>> getSheetDataMap() {
        return sheetDataMap;
    }
}