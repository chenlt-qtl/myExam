package com.exam.singleton.lazy;

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
