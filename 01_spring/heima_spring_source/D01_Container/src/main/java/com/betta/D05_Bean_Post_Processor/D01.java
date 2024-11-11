package com.betta.D05_Bean_Post_Processor;


import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Bean 后处理器的作用
 */
public class D01 {
    public static void main(String[] args) {
        // GenericApplicationContext是一个干净的容器
        GenericApplicationContext context = new GenericApplicationContext();

        //用原始方法注册三个Bean
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);
        context.registerBean("bean4",Bean4.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); //@Autowire @Value
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());//ConfigurationProperties

        context.registerBean(CommonAnnotationBeanPostProcessor.class);//@Resource @PostConstruct @PreDestroy
        //初始化容器
        context.refresh();//执行BeanFactory后处理器  添加bean后处理器 初始化所有单例

        System.out.println(context.getBean(Bean4.class));
        //销毁容器
        context.close();

    }
}
