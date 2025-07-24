package com.exam.singleton.lazy;


/**
 * 性能高了，线程安全了
 * 可读性低，不够优雅
 */
public class LazyDoubleCheckSingleton {
    private static volatile LazyDoubleCheckSingleton instance;
    private LazyDoubleCheckSingleton() {}
    public static synchronized LazyDoubleCheckSingleton getInstance() {

        //检查是否要阻塞
            if (instance == null) {
                synchronized (LazyDoubleCheckSingleton.class) {
                    //检查是否要创建实例
                    if (instance == null) {
                        instance = new LazyDoubleCheckSingleton();
                        //指令重排的问题
                    }
                }
            }
            return instance;
    }
}
