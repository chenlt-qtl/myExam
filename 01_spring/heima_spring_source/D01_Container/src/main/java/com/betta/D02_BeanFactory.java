package com.betta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

public class D02_BeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //bean的定义（class scope）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",
                beanDefinition);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        //给beanFactory添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        //执行beanFactory后处理器，补充了一些Bean定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

//        System.out.println(beanFactory.getBean(Bean1.class).getBean2()); //null 如果此时取出来打印了  后面就不会再注入bean2了 所以要注释掉

        //bean后处理器，针对bean的生命周期的各个阶段提供扩展，例如@Autowire @Resource
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream().sorted(beanFactory.getDependencyComparator()).forEach(beanPostProcessor -> {
            System.out.println(">>>>==" + beanPostProcessor);
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });

        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        beanFactory.preInstantiateSingletons();//预先实例化单例
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println(beanFactory.getBean(Bean1.class).getBean2()); //成功注入

        System.out.println(beanFactory.getBean(Bean1.class).getBean3());

    }

    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    static class Bean1 {
        public Bean1() {
            System.out.println("构造 bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Inter getBean3() {
            return bean3;
        }
    }

    static class Bean2 {
        public Bean2() {
            System.out.println("构造 bean2()");
        }
    }

    interface Inter {
    }

    static class Bean3 implements Inter {
    }

    static class Bean4 implements Inter {
    }
}
