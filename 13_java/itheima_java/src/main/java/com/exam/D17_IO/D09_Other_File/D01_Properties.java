package com.exam.D17_IO.D09_Other_File;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class D01_Properties {
    public static void main(String[] args) throws Exception {
        //读取数据
        Properties properties = new Properties();
        properties.load(new FileReader("file\\users.properties"));
        System.out.println(properties);
        System.out.println(properties.get("admin"));
        properties.stringPropertyNames().forEach(System.out::println);
        properties.forEach((k,v)-> System.out.println(k+"===="+v));


        //保存数据
        properties.setProperty("mike","444");
        properties.store(new FileWriter("file\\users.properties"),"test");
    }
}
