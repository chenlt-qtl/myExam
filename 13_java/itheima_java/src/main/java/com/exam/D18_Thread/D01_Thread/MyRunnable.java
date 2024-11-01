package com.exam.D18_Thread.D01_Thread;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("子线程输出:" + i);
        }
    }
}
