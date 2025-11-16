package org.exam.use;

import com.alibaba.excel.EasyExcel;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 读取示例
 */

public class CarInfoExcelReader {

    // 目标文件路径（固定为指定路径）
//    private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\滴滴对账单202508.xlsx";
    private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\车档信息-TEST.xlsx";
    //private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\IB-TEST.xlsx";
    //private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\晶硅切片指标数据.xlsx";
//    private static final String EXCEL_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\销售数据测试-1.xlsx";

    private static final String H2_URL = "jdbc:h2:mem:excelimport;DB_CLOSE_DELAY=-1";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    /**
     * 读取Excel文件并按Sheet名称返回数据Map
     */
    public static void readCarInfoExcel() {

        long start = System.currentTimeMillis();
        // 初始化H2数据源
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource = new JdbcDataSource();
        dataSource.setURL(H2_URL);
        dataSource.setUser(H2_USER);
        dataSource.setPassword(H2_PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        // 创建监听器
        CarInfoSheetListener listener = new CarInfoSheetListener(jdbcTemplate);

        // 流式读取Excel（自动处理大文件，避免内存溢出）
        EasyExcel.read(EXCEL_FILE_PATH)
                .headRowNumber(1) // 假设第1行为表头（若无表头请改为0）
                .registerReadListener(listener)
                .doReadAll(); // 读取所有Sheet
        System.out.println("最终SQL："+listener.getFinalDdls());
        System.out.println("耗时:"+(System.currentTimeMillis()-start)/1000+"s");
    }

    // 测试方法：验证读取结果
    public static void main(String[] args) {
        try {
            readCarInfoExcel();
        } catch (Exception e) {
            System.err.println("读取Excel失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}