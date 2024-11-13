package com.seed.app;

import com.seed.bean.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Xml方式声明Bean
 */
public class App10 {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext1.xml");
        Object cat = ctx.getBean("cat");
        System.out.println(cat);
        Dog bean = ctx.getBean(Dog.class);
        System.out.println(bean);

        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }
}
