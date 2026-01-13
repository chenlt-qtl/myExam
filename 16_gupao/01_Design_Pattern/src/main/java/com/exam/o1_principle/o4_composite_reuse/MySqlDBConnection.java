package com.exam.o1_principle.o4_composite_reuse;

public class MySqlDBConnection extends DBConnection {
    public String getConnection() {
        return "获取MySql数据连接";
    }
}
