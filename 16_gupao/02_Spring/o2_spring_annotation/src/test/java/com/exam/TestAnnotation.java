package com.exam;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAnnotation {

    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        Emp emp = (Emp)context.getBean("myEmp");
        System.out.println(emp);
    }
}
