package org.exam.use;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

// 批量插入任务（多线程执行的核心）
public class InsertTask implements Runnable {
    private final List<List<String>> batchData;
    private final DataSource dataSource;
    private final String insertSql;

    public InsertTask(List<List<String>> batchData, DataSource dataSource, String insertSql) {
        this.batchData = batchData;
        this.dataSource = dataSource;
        this.insertSql = insertSql;
    }

    @Override
    public void run() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 每个任务独立获取连接
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(insertSql);

            // 设置参数并添加到批次
            for (List<String> row : batchData) {
                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    if (value == null) {
                        stmt.setNull(i + 1, Types.VARCHAR);
                    } else {
                        stmt.setString(i + 1, value);
                    }
                }
                stmt.addBatch();
            }

            // 执行批量插入
            stmt.executeBatch();
            conn.commit();
            System.out.printf("线程[%s]插入完成，批次大小：%d%n",
                    Thread.currentThread().getName(), batchData.size());

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.printf("线程[%s]插入失败：%s%n",
                    Thread.currentThread().getName(), e.getMessage());
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}