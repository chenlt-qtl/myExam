package com.exam.o2_pattern.o1_singleton.o1_hungry;

/**
 * 优点 效率高 性能高
 * 缺点 内存浪费
 */
public class HungrySingleton {

    private static HungrySingleton instance = new HungrySingleton() ;
    private HungrySingleton() {}
    public static HungrySingleton getInstance() {
        return instance;
    }
}
