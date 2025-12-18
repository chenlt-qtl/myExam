package com.exam.D19_Thread.D01_Thread;

public class D01_InnerClass {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("子线程1输出:" + i);
                }
            }
        });
        thread.start();

        /**
         * 简化
         */
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("子线程2输出:" + i);
            }

        });
        thread1.start();

        for (int i = 1; i <= 5; i++) {
            System.out.println("主线程输出:" + i);
        }
    }
}
