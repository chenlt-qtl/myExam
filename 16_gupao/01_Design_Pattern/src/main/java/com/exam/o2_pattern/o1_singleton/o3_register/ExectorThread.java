package com.exam.o2_pattern.o1_singleton.o3_register;

public class ExectorThread implements Runnable {
    @Override
    public void run() {
        Object instance = ContainerSingleton.getInstance("com.exam.o2_pattern.o1_singleton.register.Pojo");
        System.out.println(Thread.currentThread().getName()+" : "+instance);
    }
}
