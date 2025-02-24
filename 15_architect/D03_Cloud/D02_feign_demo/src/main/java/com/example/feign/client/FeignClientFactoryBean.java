package com.example.feign.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class FeignClientFactoryBean implements FactoryBean<Object> {

    private String name;
    private String type;

    private Class<?> aClass;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, (proxy, method, args) -> {
            System.out.println(String.format("proxy execute, method:%s,args:%s", method.getName(), Arrays.toString(args)));
            //如果是Object的方法，直接执行
            if (ReflectionUtils.isObjectMethod(method)) {
                return method.invoke(aClass, args);
            }
            //执行代理方法
            else {
                return String.format("----name:%s, type:%s", name, type);
            }

        });
    }

    @Override
    public Class<?> getObjectType() {
        return aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }
}
