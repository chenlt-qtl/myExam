package com.exam.D03_Grammar;

public class D06_TypeConversion {

    public static void main(String[] args) {
        //目标理解自动类型转换机制
        //小的自动转换成大的
        byte a = 12;
        short b = a;
        int c = b;
        long d = c;
        float e = d;
        double f = e;

        //char类型的转换
        char g = 'a';
        int h = g ;
        System.out.println(h);//97
    }


}
