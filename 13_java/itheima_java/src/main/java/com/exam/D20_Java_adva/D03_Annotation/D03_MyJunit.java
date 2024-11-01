package com.betta.d41_annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 模拟Junit框架  只要加了MyTest注解的，就会触发该方法执行
 */
public class D03_MyJunit {

    public void test1() {
        System.out.println("test1...");
    }

    @MyTest
    public void test2() {
        System.out.println("test2...");
    }


    public void test3() {
        System.out.println("test3...");

    }

    @MyTest
    public void test4() {
        System.out.println("test4...");

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        D03_MyJunit d03MyJunit = new D03_MyJunit();

        //获得class对象
        Class c = d03MyJunit.getClass();

        //获取全部成员方法
        Method[] methods = c.getDeclaredMethods();

        //判断是否存在@MyTest注解
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyTest.class)){
                //执行方法
                method.invoke(d03MyJunit);
            }
        }
    }
}
