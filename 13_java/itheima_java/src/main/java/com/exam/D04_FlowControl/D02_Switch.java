package com.exam.D04_FlowControl;

public class D02_Switch {
    public static void main(String[] args) {
        //利用穿透性简化代码
        String week1 = "周六";

        switch (week1) {
            case "周一":
            case "周二":
            case "周三":
            case "周四":
            case "周五":
                System.out.println("工作");
                break;
            case "周六":
            case "周日":
                System.out.println("放假");
                break;
            default:
                System.out.println("没有处理程序");
        }
    }
}
