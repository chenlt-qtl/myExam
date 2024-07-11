package com.betta.scanner;

import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您的年龄:");
        int i = sc.nextInt();
        System.out.println("您的年龄是:" + i);
    }
}
