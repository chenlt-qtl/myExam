package com.itheima.rpc.client.spring;

import com.itheima.rpc.annotation.HrpcRemote;
import com.itheima.rpc.client.proxy.RequestProxyFactory;
import com.itheima.rpc.proxy.ProxyFactory;
import com.itheima.rpc.spring.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Configuration
@Slf4j
public class RpcAnnotationProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ProxyFactory proxyFactory;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //获取所有字段
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            HrpcRemote annotation = field.getAnnotation(HrpcRemote.class);
            //如果含有HrpcRemote注释 要生成代理
            if(annotation!=null){
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                //基于field的类型生成代理
                Class<?> aClass = field.getType();
                Object object = proxyFactory.newProxyInstance(aClass);
                if(object!= null){
                    try {
                        field.set(bean,object);
                    } catch (IllegalAccessException e) {
                        log.error("failed to inject proxy ,field={}",field.getName());
                    }
                }
            }

        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        proxyFactory = applicationContext.getBean(ProxyFactory.class);
    }
}
