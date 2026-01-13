package com.exam.o2_pattern.o1_singleton.o2_lazy;

/**
 * 节省内存
 *  性能低
 */
public class LazySimpleSingleton {
    private static LazySimpleSingleton instance;
    private LazySimpleSingleton() {}
    public synchronized static LazySimpleSingleton getInstance() {
        if (instance == null) {
            instance = new LazySimpleSingleton();
        }
        return instance;
    }
}
