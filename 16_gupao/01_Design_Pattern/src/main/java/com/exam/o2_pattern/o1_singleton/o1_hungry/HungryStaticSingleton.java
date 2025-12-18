package com.exam.o2_pattern.o1_singleton.o1_hungry;

/**
 * 与第一种一样
 */
public class HungryStaticSingleton {
    private static HungryStaticSingleton instance;
    static {
        instance = new HungryStaticSingleton();
    }
    private HungryStaticSingleton() {}
    public static HungryStaticSingleton getInstance() {
        return instance;
    }
}
