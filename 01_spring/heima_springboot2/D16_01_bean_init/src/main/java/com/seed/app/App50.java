package com.seed.app;

import com.seed.bean.Cat;
import com.seed.bean.Dog;
import com.seed.bean.Mouse;
import com.seed.config.SpringConfig4;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 后注册方法加载Bean
 */
public class App50 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig4.class);

        //上下文容器对象已经初始化完毕后，手工加载bean
        ctx.registerBean("seed", Cat.class);
        ctx.registerBean("seed", Dog.class);//覆盖了上一行的Cat

        ctx.register(Mouse.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(ctx.getBean("seed"));
    }
}
