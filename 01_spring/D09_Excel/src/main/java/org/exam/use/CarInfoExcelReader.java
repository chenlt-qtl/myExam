package org.exam.use;

import com.alibaba.excel.EasyExcel;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;
import java.util.Map;

/**
 * 读取示例
 */

public class CarInfoExcelReader {

    // 目标文件路径（固定为指定路径）
    //private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\滴滴对账单202508.xlsx";
    private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\车档信息-TEST.xlsx";

    private static final String H2_URL = "jdbc:h2:mem:excelimport;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    /**
     * 读取Excel文件并按Sheet名称返回数据Map
     */
    public static Map<String, List<Map<Integer, String>>> readCarInfoExcel() {

        // 初始化H2数据源
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);
        dataSource.setUser(H2_USER);
        dataSource.setPassword(H2_PASSWORD);

        // 创建监听器
        CarInfoSheetListener listener = new CarInfoSheetListener(dataSource);

        // 流式读取Excel（自动处理大文件，避免内存溢出）
        EasyExcel.read(EXCEL_FILE_PATH)
                .headRowNumber(1) // 假设第1行为表头（若无表头请改为0）
                .registerReadListener(listener)
                .doReadAll(); // 读取所有Sheet
        System.out.println("最终SQL："+listener.getFinalDdls());
        return listener.getSheetDataMap();
    }

    // 测试方法：验证读取结果
    public static void main(String[] args) {
        try {
            Map<String, List<Map<Integer, String>>> carDataMap = readCarInfoExcel();

            // 遍历输出各Sheet信息
            for (Map.Entry<String, List<Map<Integer, String>>> entry : carDataMap.entrySet()) {
                String sheetName = entry.getKey();
                List<Map<Integer, String>> sheetData = entry.getValue();

                System.out.println("Sheet名称：" + sheetName);
                System.out.println("数据总行数：" + sheetData.size());
                // 打印前2行数据（避免数据过多刷屏）
                if (!sheetData.isEmpty()) {
                    System.out.println("前2行数据示例：");
                    for (int i = 0; i < Math.min(2, sheetData.size()); i++) {
                        System.out.println("第" + (i + 1) + "行：" + sheetData.get(i));
                    }
                }
                System.out.println("------------------------");
            }
        } catch (Exception e) {
            System.err.println("读取Excel失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}