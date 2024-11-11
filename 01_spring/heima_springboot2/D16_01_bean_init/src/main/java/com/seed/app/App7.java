package com.seed.app;

import com.seed.config.SpringConfig6;
import com.seed.config.SpringConfig7;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App7 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig7.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
