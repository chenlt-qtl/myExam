package com.seed.app;

import com.seed.config.SpringConfig32;
import com.seed.config.SpringConfig33;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App33 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig33.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        SpringConfig33 springConfig33 = ctx.getBean("springConfig33", SpringConfig33.class);
        System.out.println(springConfig33.cat());
        System.out.println(springConfig33.cat());
        System.out.println(springConfig33.cat());
    }
}
