package com.exam.D22_exam.d01_order_xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 需求
 * ·某小型商城系统的订单信息在素材下的orders.xml文件中，现在要求把xm!中的订单信息，封装成一个一个的订单对象，将订单对象保存到ArrayList集合中。
 * **具体功能点要求**
 * 1)定义订单类Order，创 建ArrayList集合，用于存储订单Order对象(解析XML 4分，封装成对象2分)
 * 2)请使用Stream流找出2023年11月11日之前的订单，并遍历输出。(3分)
 * 3)请使用Stream流找出集合中价格最贵的订流单，把这个订单的详细信息打印出来。(3分)
 * 4)请使用Stream流遍历集合中的每个订单，要求按照价格降序输出每个订单的详情。(3分)
 */
public class App {

    public static void main(String[] args) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("file\\orders.xml");

        //1)定义订单类Order，创 建ArrayList集合，用于存储订单Order对象(解析XML 4分，封装成对象2分)
        //存放结果
        List<Order> list = new ArrayList<>();

        //获取根元素
        Element root = document.getRootElement();
        System.out.println(root.getName());//orders
        //获取子元素
        List<Element> orders = root.elements("order");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orders.forEach(e -> {
            Order order = new Order();
            order.setId(Integer.valueOf((String) e.attributeValue("id")));
            order.setName(e.elementText("name"));
            order.setTime(LocalDateTime.parse(e.elementTextTrim("time"),formatter));
            order.setPrice(Double.valueOf(e.elementTextTrim("double")));
            list.add(order);
        });
        System.out.println("全部订单-----------");
        System.out.println(list);

        //2)请使用Stream流找出2023年11月11日之前的订单，并遍历输出。(3分)
        System.out.println("23年双十一之前的订单-----------");
        LocalDate localDate = LocalDate.of(2023, 11, 11);
        list.stream().filter(order -> order.getTime().toLocalDate().isBefore(localDate))
                .forEach(System.out::print);


        //3)请使用Stream流找出集合中价格最贵的订流单，把这个订单的详细信息打印出来。(3分)
        System.out.println("最贵的订单-----------");
        Optional<Order> max = list.stream().max(Comparator.comparingDouble(Order::getPrice));
        System.out.print(max.get());

        //4)请使用Stream流遍历集合中的每个订单，要求按照价格降序输出每个订单的详情。(3分)
        System.out.println("按照价格降序输出----------");
        list.stream().sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice())).forEach(System.out::print);

    }
}
