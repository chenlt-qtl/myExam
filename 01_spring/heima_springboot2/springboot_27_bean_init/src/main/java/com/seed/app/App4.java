package com.seed.app;

import com.seed.config.SpringConfig33;
import com.seed.config.SpringConfig4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App4 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig4.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
