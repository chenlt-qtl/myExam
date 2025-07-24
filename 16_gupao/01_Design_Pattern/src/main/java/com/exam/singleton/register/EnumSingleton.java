package com.exam.singleton.register;

/**
 * 枚举单例
 */
public enum EnumSingleton {
    INSTANCE;
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
