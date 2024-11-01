package com.exam.D11_API;

import java.math.BigDecimal;

public class D02_BigDecimal {
    public static void main(String[] args) {
        double a = 0.1;
        double b = 0.2;
        double c = a + b;
        System.out.println(c);//0.30000000000000004

        //错误
        BigDecimal a1 = new BigDecimal(0.1);
        BigDecimal b1 = new BigDecimal(0.2);
        BigDecimal c1 = a1.add(b1);
        System.out.println(c1);//0.3000000000000000166533453693773481063544750213623046875

        //正确
        BigDecimal a2 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        BigDecimal c2 = b2.add(a2);
        System.out.println(c2);//0.3

        //推荐
        BigDecimal a3 = BigDecimal.valueOf(0.1);
        BigDecimal b3 = BigDecimal.valueOf(0.2);
        BigDecimal c3 = b3.add(a3);//加
        System.out.println(c3);//0.3
        System.out.println(b3.subtract(a3));//减法
        System.out.println(b3.divide(a3));//除法
        System.out.println(b3.multiply(a3));//乘法;
    }
}
