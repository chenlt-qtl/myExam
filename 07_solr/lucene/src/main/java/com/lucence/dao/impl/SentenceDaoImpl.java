package com.lucence.dao.impl;

import com.lucence.bean.Sentence;
import com.lucence.dao.SentenceDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SentenceDaoImpl implements SentenceDao {

    @Override
    public List<Sentence> getList() {
        List<Sentence> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //创建数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/corn", "corn", "corn2020!@#");
            //sql语句
            String sql = "select * from word_sentence";
            //执行sql语句
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            //遍历结果集
            while (resultSet.next()) {
                Sentence sentence = new Sentence();
                sentence.setId(resultSet.getString("id"));
                sentence.setContent(resultSet.getString("content"));
                sentence.setIdx(resultSet.getInt("idx"));
                sentence.setCreateTime(resultSet.getDate("create_time"));
                list.add(sentence);
            }
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.close();
                }
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }
}
