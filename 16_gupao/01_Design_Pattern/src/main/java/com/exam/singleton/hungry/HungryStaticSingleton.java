package com.exam.singleton.hungry;

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
