package com.exam.D18_Thread.D01_Thread;

/**
 * 使用thread的方法
 */
public class D05_ThreadName {
    public static void main(String[] args) {
        Thread t1 = new MyThread("张三");
        Thread t2 = new MyThread("李四");
        t1.start();
        t2.start();

        Thread mainThread = Thread.currentThread();
        mainThread.setName("王五");
        String name = mainThread.getName();
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + "线程输出：" + i);
        }
    }
}
