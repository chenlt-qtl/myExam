package com.exam.o2_pattern.o1_singleton.o4_seriable;

import java.io.Serializable;

public class SeriableSingleton implements Serializable {
    private final static SeriableSingleton INSTANCE = new SeriableSingleton();
    private SeriableSingleton() {}
    public static SeriableSingleton getInstance() {return INSTANCE;}

    /**
     * 解决序列化破坏单例
     * @return
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
