package com.exam.D20_Java_adva.D02_Reflect;


import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class D04_MethodDemo {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Cat> c = Cat.class;

        //获取全部成员方法
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + "--->" + method.getParameterCount() + "--->" + method.getReturnType());
        }

        System.out.println("**************************");

        //获取某个成员方法
        Method run = c.getDeclaredMethod("run");
        System.out.println(run.getName() + "--->" + run.getParameterCount() + "--->" + run.getReturnType());

        Method eat = c.getDeclaredMethod("eat",String.class);
        System.out.println(eat.getName() + "--->" + eat.getParameterCount() + "--->" + eat.getReturnType());

        System.out.println("**************************");

        //执行方法
        Cat cat = new Cat();
        run.setAccessible(true);
        run.invoke(cat);

        eat.setAccessible(true);
        System.out.println(eat.invoke(cat, "猫条"));
    }
}
