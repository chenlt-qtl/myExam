package com.exam.D19_Thread.D01_Thread;

public class D04_Runnable {
    public static void main(String[] args) {
        Runnable r = new MyRunnable();
        new Thread(r).start();
        for (int i = 1; i <= 5; i++) {
            System.out.println("主线程输出:" + i);
        }
    }
}

