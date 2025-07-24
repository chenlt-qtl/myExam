package com.exam.singleton.lazy;

public class ExectorThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+" : "+singleton);
    }
}
