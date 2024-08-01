package com.betta.enumDemo;

public class Demo {
    public static void main(String[] args) {
        A x = A.X;
        System.out.println(x);
        B b = B.X;
        System.out.println(b);
        b.go();
    }
}
