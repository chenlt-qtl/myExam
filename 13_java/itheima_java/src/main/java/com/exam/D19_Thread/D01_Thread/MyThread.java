package com.exam.D19_Thread.D01_Thread;

public class MyThread extends Thread {

    public MyThread() {

    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {

        Thread thread = Thread.currentThread();
        String name = thread.getName();

        for (int i = 1; i <= 5; i++) {
            System.out.println(name + "线程输出：" + i);
        }
    }
}