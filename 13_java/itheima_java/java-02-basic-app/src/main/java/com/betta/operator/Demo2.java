package com.betta.operator;

public class Demo2 {
    public static void main(String[] args) {
        // ^  异或   前后条件结果相同，返回false,前后条件的结果不同，返回true
        System.out.println(true ^ true);//false
        System.out.println(true ^ false);//true

        //&& | 短路与| 左边为false则右边不执行
        int j = 1;
        System.out.println(2 < 1 && ++j > 0);
        System.out.println(j);//1

        // || 短路或 左边为true则右边不执行
        System.out.println(2 > 1 || ++j > 0);
        System.out.println(j);//1
    }
}
