package com.seed.app;

import com.seed.bean.Cat;
import com.seed.bean.Dog;
import com.seed.bean.Mouse;
import com.seed.config.SpringConfig4;
import com.seed.config.SpringConfig6;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App6 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig6.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
