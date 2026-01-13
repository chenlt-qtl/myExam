package com.exam.D21_Java_adva.D02_Reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class D02_ConstructorDemo {
    @Test
    public void testGetConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Cat> catClass = Cat.class;

        //获取public构造器
        Constructor<?>[] constructors = catClass.getConstructors();

        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor.getName() + "--->" + constructor.getParameterCount());
        }

        System.out.println("------------------------");

        //获取所有构造器
        Constructor<?>[] constructors1 = catClass.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors1) {
            System.out.println(constructor.getName() + "--->" + constructor.getParameterCount());
        }

        System.out.println("------------------------");

        Constructor<Cat> constructor = catClass.getConstructor();
        System.out.println(constructor.getName() + "--->" + constructor.getParameterCount());

        Constructor<Cat> constructor1 = catClass.getDeclaredConstructor(String.class,int.class);
        System.out.println(constructor1.getName() + "--->" + constructor1.getParameterCount());

        System.out.println("------------------------");
        //使用构造器创建对象

        Cat cat = constructor.newInstance();
        System.out.println(cat);

        Cat cat1 = constructor1.newInstance("abc",4);
        System.out.println(cat1);

        System.out.println("------------------------");
        //使用私有构造器创建对象
        Constructor<Cat> constructor2 = catClass.getDeclaredConstructor(int.class);
        System.out.println(constructor2.getName() + "--->" + constructor2.getParameterCount());

        //设置为可访问 暴力反射
        constructor2.setAccessible(true);
        Cat cat2 = constructor2.newInstance(3);
        System.out.println(cat2);

    }
}
