package com.exam.D21_Java_adva.D03_Annotation;

import org.junit.Test;

import java.util.Arrays;

/**
 * 注解的解析
 */
public class D02_GetAnnotationDemo {

    @Test
    public void test(){
        Class<Demo> c = Demo.class;
        if(c.isAnnotationPresent(MyAnnotation3.class)) {
            MyAnnotation3 annotation = c.getDeclaredAnnotation(MyAnnotation3.class);
            System.out.println(annotation.aaa());
            System.out.println(Arrays.toString(annotation.bbb()));
            System.out.println(annotation.value());
        }
    }
}


@MyAnnotation3(value="c",bbb={"d","8888"})
class Demo {

    @MyAnnotation3(value = "test1",bbb={"666"})
    public void test1(){

    }
}