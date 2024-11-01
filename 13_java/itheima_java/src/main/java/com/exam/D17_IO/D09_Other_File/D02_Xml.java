package com.exam.D17_IO.D09_Other_File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class D02_Xml {
    public static void main(String[] args) throws DocumentException {
        //读取文件
        //创建一个Dom4j的解析器对象
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("file\\users.xml");
        //获取根元素
        Element root = document.getRootElement();
        System.out.println(root.getName());//users
        //获取子元素
        List<Element> users = root.elements("user");
        users.forEach(e->{
            System.out.println(e.getName());
            System.out.println(e.attribute("id").getData());
            System.out.println(e.elementTextTrim("name"));
            System.out.println(e.element("age").getTextTrim());
        });

        //写入文件
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!-- 以上抬头声明必须放在第一行，必须有-->\n" +
                "<!-- 根标签只能有一个-->\n" +
                "<users>\n" +
                "    <user id=\"1\">\n" +
                "        <name>tom</name>\n" +
                "        <age>7</age>\n" +
                "    </user>\n" +
                "</users>");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("file\\user1.xml"))){
            bw.write(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
