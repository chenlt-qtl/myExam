package com.betta.d20_collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 遍历
 */
public class Demo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Tom");
        list.add("Mike");
        list.add("Joey");
        list.add("Chandler");

        //iterator
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("-----------------------------");
        //增强for循环
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("------------------------");
        //lambda表达式
        list.forEach(System.out::println);
    }
}
