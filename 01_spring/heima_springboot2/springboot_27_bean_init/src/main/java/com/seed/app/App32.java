package com.seed.app;

import com.seed.config.SpringConfig3;
import com.seed.config.SpringConfig32;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App32 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig32.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
