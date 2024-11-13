package com.seed.app;

import com.seed.config.SpringConfig32;
import com.seed.config.SpringConfig33;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * proxyBeanMethods设置为true  保障调用此方法得到的对象是从容器中获取而不是重新创建的
 */
public class App33 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig33.class);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        SpringConfig33 springConfig33 = ctx.getBean("springConfig33", SpringConfig33.class);
        System.out.println(springConfig33);
        //当 proxyBeanMethods 是true时
        // 对象打印出来是 com.seed.config.SpringConfig33$$EnhancerBySpringCGLIB$$884645aa@71238fc2
        //三次调用cat()方法得到的对象是同一个

        //当 proxyBeanMethods 是false时
        // 对象打印出来是 com.seed.config.SpringConfig33@3c46e67a
        //三次调用cat()方法得到的对象是不一样的

        System.out.println(springConfig33.cat());
        System.out.println(springConfig33.cat());
        System.out.println(springConfig33.cat());
    }
}
