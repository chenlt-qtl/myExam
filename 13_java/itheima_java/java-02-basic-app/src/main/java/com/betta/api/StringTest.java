package com.betta.api;

public class StringTest {
    public static void main(String[] args) {
        String a = "abc";
        String b = "a" + "b" + "c";//编辑器会优化成  String b = "abc";
        System.out.println(a == b);//true

        String a1 = "abc";
        String a2 = "ab";
        String b1 = a2 + "c";
        System.out.println(a1 == b1);//false

        String a3 = new String("abc");
        String b3 = new String("abc");
        System.out.println(a3 == b3);//false
    }
}
