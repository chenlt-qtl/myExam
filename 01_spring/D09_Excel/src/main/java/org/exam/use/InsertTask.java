package org.exam.use;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

// 批量插入任务（多线程执行的核心）
public class InsertTask implements Runnable {
    private final List<List<String>> batchData;
    private final JdbcTemplate jdbcTemplate;
    private final String insertSql;

    public InsertTask(List<List<String>> batchData, JdbcTemplate jdbcTemplate, String insertSql) {
        this.batchData = batchData;
        this.jdbcTemplate = jdbcTemplate;
        this.insertSql = insertSql;
    }

    @Override
    public void run() {

        try {

            // 使用JdbcTemplate执行批量操作
            jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    // 设置第i行数据的参数
                    List<String> row = batchData.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        String value = row.get(j);
                        if (value == null) {
                            ps.setNull(j + 1, Types.VARCHAR);
                        } else {
                            ps.setString(j + 1, value);
                        }
                    }
                }

                @Override
                public int getBatchSize() {
                    // 返回当前批次的数据量
                    return batchData.size();
                }
            });

            System.out.printf("线程[%s]插入完成，批次大小：%d%n",
                    Thread.currentThread().getName(), batchData.size());

        } catch (Exception e) {
            System.err.printf("线程[%s]插入失败：%s%n",
                    Thread.currentThread().getName(), e.getMessage());
        }
    }
}