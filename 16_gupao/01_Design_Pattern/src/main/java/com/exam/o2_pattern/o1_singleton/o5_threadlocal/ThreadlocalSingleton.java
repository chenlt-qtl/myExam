package com.exam.o2_pattern.o1_singleton.o5_threadlocal;

public class ThreadlocalSingleton {
    private static final ThreadLocal<ThreadlocalSingleton> threadlocalInstance =
            new ThreadLocal<>(){
                @Override
                protected ThreadlocalSingleton initialValue() {
                    return new ThreadlocalSingleton();
                }
            };

    private ThreadlocalSingleton() {}
    public static ThreadlocalSingleton getInstance() {
        return threadlocalInstance.get();
    }
}
