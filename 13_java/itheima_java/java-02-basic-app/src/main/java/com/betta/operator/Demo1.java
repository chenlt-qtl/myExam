package com.betta.operator;

public class Demo1 {
    public static void main(String[] args) {
        System.out.println(5 / 2);//2
        System.out.println(5.0 / 2);//2.5
        System.out.println(1.0 * 5 / 2);//2.5

        System.out.println("---------加号做连接符---------");
        int a = 5;
        System.out.println("a" + 5);//a5
        System.out.println(a + 5);//10
        System.out.println(a + 5 + "a");//10a
        System.out.println(a + 5 + 'a');//107

        System.out.println("---------------自增自减----------------");
        int m = 5;
        int n = 3;
        int b = ++m - --m + m-- - ++n + n-- + 3;
        System.out.println(b);//9
    }
}
