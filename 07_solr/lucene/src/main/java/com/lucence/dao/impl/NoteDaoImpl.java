package com.lucence.dao.impl;

import com.lucence.bean.Note;
import com.lucence.dao.NoteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    @Override
    public List<Note> getNoteList() {
        List<Note> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //创建数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/corn", "corn", "corn2020!@#");
            //sql语句
            String sql = "select * from note_info";
            //执行sql语句
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            //遍历结果集
            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getString("id"));
                note.setName(resultSet.getString("name"));
                list.add(note);
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
