package com.exam.D03_Grammar;

public class D07_TypeConversion {

    public static void main(String[] args) {
        byte a = 10;
        int b = 20;
        long c = 30;
        long rs = a + b + c;
        System.out.println(rs);
        double v = a + b + 0.1;//转成大的类型 double
        System.out.println(v);

        byte i = 10;
        short j = 30;
        int k = i + j;//i j已经转成int

        //强制类型转换
        int l = 10;
        byte m = (byte) l;

        System.out.println(m); //10

        int n = 1500;
        System.out.println((byte)n);//-36


        double o = 9.344;
        System.out.println((int)o);// 9
    }


}
