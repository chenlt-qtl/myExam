package com.exam.D11_API;

import java.util.StringJoiner;

public class D01_StringBuffer {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        //拼接内容
        s.append(123);
        s.append('b');
        s.append(true);
        System.out.println(s);

        //支持链式编程
        s.append(1).append(3).append('c');
        System.out.println(s);
        s.reverse();
        System.out.println(s);

        //StringBuilder的好处
        System.out.println("---------StringBuilder的好处----------");
        System.out.println("拼接10000次ABC");
        long start = System.currentTimeMillis();
        String string = "";
        for (int i = 0; i < 100000; i++) {
            string += "abc";
        }
        System.out.println(string.length() + "，耗时：" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            stringBuilder.append("abc");
        }
        System.out.println(stringBuilder.length() + "，耗时：" + (System.currentTimeMillis() - start));

        System.out.println("--------StringJoiner--------");
        System.out.println("--------把数组打印成 [1, 44, 66, 88]格式--------");
        //第一个参数是分隔符，第二个参数是开始符号，第三个参数是结束符号
        StringJoiner stringJoiner = new StringJoiner(", ","[","]");
        int[] arr = new int[]{1,44,66,88};
        for (int i = 0; i < arr.length; i++) {
            stringJoiner.add(String.valueOf(arr[i]));
        }
        System.out.println(stringJoiner);
    }
}
