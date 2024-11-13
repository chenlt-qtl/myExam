package com.betta;

public class D01_SpringFormat {

    public static void main(String[] args) {
        System.out.println(String.format("Hi,%s", "小超"));//Hi,小超
        System.out.println(String.format("Hi,%s %s %s", "小超", "是个", "大帅哥"));//Hi,小超 是个 大帅哥
        System.out.println(String.format("字母c的大写是:%c",'C'));//字母c的大写是:C
        System.out.println(String.format("布尔结果是:%b %n","小超".equals("帅哥")));//布尔结果是:false
        System.out.println(String.format("100的一半是:%d %n",100/2));//100的一半是:50
        System.out.println(String.format("100的16进制数是:%x%n",100));//100的16进制数是:64
        System.out.println(String.format("100的8进制数是:%o%n",100));//100的8进制数是:144
        System.out.println(String.format("50元的书打8.5折扣是:%f元%n",50*0.85));//50元的书打8.5折扣是:42.500000元
        System.out.println(String.format("上面价格的16进制数是:%a %n",50*0.85));//上面价格的16进制数是:0x1.54p5
        System.out.println(String.format("上面价格的指数表示:%e %n",50*0.85));//上面价格的指数表示:4.250000e+01
        System.out.println(String.format("上面价格的指数和浮点数结果的长度较短的是:%g%n",50*0.85));//上面价格的指数和浮点数结果的长度较短的是:42.5000
        System.out.println(String.format("上面的折扣是%d%%%n",85));//上面的折扣是85%
        System.out.println(String.format("字母A的散列码是:%h %n",'A'));//字母A的散列码是:41

        double num = 123.4567899;

        System.out.print(String.format("%f %n", num)); // 123.456790
        System.out.println(String.format("%f", num)); // 123.456790
        System.out.print(String.format("%a %n", num)); // 0x1.edd3c0bb46929p6
        System.out.println(String.format("%f", num)); // 123.456790
        System.out.print(String.format("%g %n", num)); // 123.457
        System.out.println(String.format("%g", num)); // 123.456790

        System.out.println(String.format("%(,d", -1000)); //(1,000)
    }
}
