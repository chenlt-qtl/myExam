package com.exam.o2_pattern.o1_singleton.o5_threadlocal;

/**
 * 在不同线程会返回不同的对象
 * 同一个线程是同一个对象
 */
public class Test {
    public static void main(String[] args) {

        System.out.println(ThreadlocalSingleton.getInstance());
        System.out.println(ThreadlocalSingleton.getInstance());
        System.out.println(ThreadlocalSingleton.getInstance());


        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());
        Thread t3 = new Thread(new ExectorThread());
        t1.start();
        t2.start();
        t3.start();
    }
}
