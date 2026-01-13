package com.exam.o2_pattern.o1_singleton.o2_lazy;

public class ExectorThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+" : "+singleton);
    }
}
