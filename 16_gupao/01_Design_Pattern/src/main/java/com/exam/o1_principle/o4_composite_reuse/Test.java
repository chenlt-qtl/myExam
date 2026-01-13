package com.exam.o1_principle.o4_composite_reuse;

public class Test {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        DBConnection dbConnection = new MySqlDBConnection();
        productDao.setConnection(dbConnection);
        productDao.addProduct();
    }
}
