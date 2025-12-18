package com.exam.o2_pattern.o1_singleton.o2_lazy;


public class LazyTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new ExectorThread());
        Thread thread2 = new Thread(new ExectorThread());
        thread1.start();
        thread2.start();
        System.out.println("end");
    }
}
