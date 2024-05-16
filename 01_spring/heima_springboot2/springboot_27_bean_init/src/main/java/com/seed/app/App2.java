package com.seed.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
