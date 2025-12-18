package com.exam.D19_Thread.D01_Thread;

public class D02_Thread {
    //main方法是由一条默认的主线程负责执行
    public static void main(String[] args) {
        Thread myThread = new MyThread();
        myThread.run();
        for (int i = 1; i <= 5; i++) {
            System.out.println("主线程输出:" + i);
        }
    }
}


