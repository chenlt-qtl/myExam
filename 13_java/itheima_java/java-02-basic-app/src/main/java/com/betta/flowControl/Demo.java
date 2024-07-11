package com.betta.flowControl;

public class Demo {
    public static void main(String[] args) {
        String week = "周一";

        switch (week) {
            case "周一":
                System.out.println("不喜欢周一");
                //遇到break跳出switch, 不然一匹配会执行接下来所有代码
//                break;
            case "周三":
                System.out.println("喝点小酒");
//                break;
            default:
                //没有匹配项执行此代码
                System.out.println("没有处理程序");
        }

    }
}
