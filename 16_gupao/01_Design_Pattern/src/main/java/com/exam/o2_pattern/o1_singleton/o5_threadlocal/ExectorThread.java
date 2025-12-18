package com.exam.o2_pattern.o1_singleton.o5_threadlocal;

import com.exam.o2_pattern.o1_singleton.o3_register.ContainerSingleton;

public class ExectorThread implements Runnable {
    @Override
    public void run() {
        Object instance = ThreadlocalSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+" : "+instance);
    }
}
