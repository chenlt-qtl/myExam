package com.exam.o2_pattern.o1_singleton.o2_lazy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射破坏单例
 */
public class ReflectTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<LazyStaticInnerSingleton> clazz = LazyStaticInnerSingleton.class;
        Constructor<LazyStaticInnerSingleton> declaredConstructor = clazz.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        LazyStaticInnerSingleton lazyStaticInnerSingleton1 = declaredConstructor.newInstance();
        LazyStaticInnerSingleton lazyStaticInnerSingleton2 = declaredConstructor.newInstance();
        System.out.println(lazyStaticInnerSingleton1);
        System.out.println(lazyStaticInnerSingleton2);

    }
}
