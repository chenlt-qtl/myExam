package com.exam.D16_Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class D01_Stream {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "张三", "李四", "王五","张乐文","李安在");
        System.out.println(list);

        //找出姓张的 且名字是三个字的
        List<String> z = list.stream().filter(n -> n.startsWith("张") && n.length() == 3).collect(Collectors.toList());

        System.out.println(z);
    }
}

