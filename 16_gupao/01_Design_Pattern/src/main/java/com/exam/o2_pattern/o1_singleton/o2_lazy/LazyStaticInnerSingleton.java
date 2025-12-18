package com.exam.o2_pattern.o1_singleton.o2_lazy;


/**
 * classPath: LazyStaticInnerSingleton.class
 *            LazyStaticInnerSingleton$LazyHolder.class
 *     优雅 利用JAVA本身语法特点 性能高 避免内存浪费
 *     缺点：能够被反射破坏
 *
 */
public class LazyStaticInnerSingleton {
    private LazyStaticInnerSingleton() {
        if(LazyHoder.instance!=null){
            throw new RuntimeException("LazyHoder instance already created");
        }
    }
    public static synchronized LazyStaticInnerSingleton getInstance() {
        return LazyHoder.instance;
    }

    private static class LazyHoder{
        private static final LazyStaticInnerSingleton instance = new LazyStaticInnerSingleton();
    }
}
