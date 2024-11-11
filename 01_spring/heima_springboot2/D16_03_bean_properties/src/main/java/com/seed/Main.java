package com.seed;

import com.seed.bean.CartoonCatAndMouse;
import com.seed.bean.Cat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration"})
//@Import(CartoonCatAndMouse.class)
public class Main {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        CartoonCatAndMouse bean = ctx.getBean(CartoonCatAndMouse.class);
        bean.play();

//        Cat cat = ctx.getBean(Cat.class);
//        System.out.println(cat);

    }
}