package com.exam.singleton.register;

import com.exam.singleton.lazy.LazyStaticInnerSingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EnumTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        EnumSingleton enumSingleton = EnumSingleton.getInstance();
        System.out.println(enumSingleton);

        //反射破坏
        Class<EnumSingleton> clazz = EnumSingleton.class;
        Constructor<EnumSingleton> declaredConstructor = clazz.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingleton singleton1 = declaredConstructor.newInstance();
        EnumSingleton singleton2 = declaredConstructor.newInstance();
        System.out.println(singleton1);
        System.out.println(singleton2);
    }
}
