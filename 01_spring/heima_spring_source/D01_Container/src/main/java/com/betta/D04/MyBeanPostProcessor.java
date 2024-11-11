package com.betta.D04;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 自定义Bean后处理器类
 */
@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    //销毁之前执行
    @Override
    public void postProcessBeforeDestruction(Object o, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<<<销毁之前执行，如@PreDestroy");
        }
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<实例化之前执行，这里返回的对象会替换掉原本的bean,返回null的话bean不变");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<实例化之后执行，这里返回的false会路过依赖注入阶段");
            //return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<依赖注入阶段执行，如@Autowired @Value @Resource");
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<初始化之前执行，这里返回的对象会替换掉原本的bean,如@PostConstruct @ConfigurationProperties");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("LifeCycleBean")) {
            System.out.println("<<<<<<<<<<<初始化之后执行，这里返回的对象会替换掉原本的bean,如代理增强");
        }
        return bean;    }
}
