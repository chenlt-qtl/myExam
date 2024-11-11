package com.betta.D01;

import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 *  BeanFactory和ApplicationContext
 */
@SpringBootApplication
public class D01_Container {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(D01_Container.class, args);

        //调用beanFactory的getBean方法
        Component1 component1 = (Component1) context.getBean("component1");
        System.out.println(component1);

        System.out.println("---------------------------------");

        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(context.getBeanFactory());
        map.keySet().stream().filter(k -> k.startsWith("component"))
                .forEach(k -> System.out.println(k + "=>" + map.get(k)));

        //applicationContext功能
        //国际化
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));


        //获取资源
        Resource[] resources = context.getResources("classpath:messages_zh.properties");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        //如果是JAR包里的文件，classpath后面要加*
        resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        //获取配置信息
        System.out.println(context.getEnvironment().getProperty("JAVA_HOME"));
        System.out.println(context.getEnvironment().getProperty("name"));

        //发布事件
        System.out.println("======发布事件============");
        context.publishEvent(new UserRegisteredEvent(context));

    }

}
