package com.exam.D15_Collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 迭代器并发错误演示
 */
public class D03_Itertor_Error {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("李玉");
        list.add("刘能");
        list.add("王五");
        list.add("李为");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String name = iterator.next();
            if(name.contains("李")){
//                list.remove(name);
                iterator.remove();
            }
        }

        System.out.println(list);
    }
}
