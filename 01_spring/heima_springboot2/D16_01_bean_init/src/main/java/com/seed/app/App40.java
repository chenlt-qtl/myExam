package com.seed.app;

import com.seed.config.SpringConfig4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *使用Import加载bean  降低源代码与spring的耦合度
 */
public class App40 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig4.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
