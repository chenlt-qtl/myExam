package com.betta.D05_Bean_Post_Processor;


import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;

/**
 * AutowiredAnnotationBeanPostProcessor运行分析
 */
public class D02_AutowiredAnnotationBeanPostProcessor {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("bean2",new Bean2());//创建过程，依赖注入，初始化
        beanFactory.registerSingleton("bean3",new Bean3());
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());//@Value

        //${}的解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);

        // 1.查找哪些属性，方法加了@Autowired 这称之为 InjectionMetadata
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
//        System.out.println(bean1);
//        System.out.println("-----------------");
//        processor.postProcessProperties(null,bean1,"bean1");
//        System.out.println(bean1);

        //findAutowiringMetadata方法获取Bean1上加了@Value @Autowired 的成员变量，方法参数信息
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);
        System.out.println(metadata);

        //2. 调用InjectionMetadata 来进行依赖注入，注入时按类型查找值
        metadata.inject(bean1,"bean1",null);
        System.out.println(bean1);

        //3. 如何按类型查找值
        System.out.println("---------如何按类型查找值--------");
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(bean3, false);
        Object o = beanFactory.doResolveDependency(dependencyDescriptor, null, null, null);
        System.out.println(o);

        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2",Bean2.class);
        dependencyDescriptor = new DependencyDescriptor(new MethodParameter(setBean2,0), false);
        o = beanFactory.doResolveDependency(dependencyDescriptor, null, null, null);
        System.out.println(o);

        Method setHome = Bean1.class.getDeclaredMethod("setHome",String.class);
        dependencyDescriptor = new DependencyDescriptor(new MethodParameter(setHome,0), false);
        o = beanFactory.doResolveDependency(dependencyDescriptor, null, null, null);
        System.out.println(o);
    }
}
