package com.exam.o2_pattern.o1_singleton.o3_register;

/**
 * 枚举单例 饿汉式（不能大批量创建对象）
 */
public enum EnumSingleton {
    INSTANCE;
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
