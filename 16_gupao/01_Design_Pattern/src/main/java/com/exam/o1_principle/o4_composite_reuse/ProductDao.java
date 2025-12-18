package com.exam.o1_principle.o4_composite_reuse;

public class ProductDao {
    private DBConnection dbConnection;
    public void setConnection(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public void addProduct(){
        String conn = dbConnection.getConnection();
        System.out.println(conn);
    }
}
