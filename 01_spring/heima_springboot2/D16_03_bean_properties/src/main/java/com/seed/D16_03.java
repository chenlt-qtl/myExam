package com.seed;

import com.seed.bean.CartoonCatAndMouse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 自动配置例子
 */
@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration"})
//@Import(CartoonCatAndMouse.class)
public class D16_03 {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(D16_03.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        CartoonCatAndMouse bean = ctx.getBean(CartoonCatAndMouse.class);
        bean.play();

//        Cat cat = ctx.getBean(Cat.class);
//        System.out.println(cat);

    }
}