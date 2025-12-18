package com.exam.o2_pattern.o7_proxy.o2_jdk.cglibProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLOutput;
import java.util.List;

public class Meipo implements InvocationHandler {
    private IPerson person;
    public IPerson getInstance(IPerson person) {
        this.person = person;
        Class clazz = IPerson.class;
        return (IPerson) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(person, args);
        after();
        return obj;
    }

    private void after() {
        System.out.println("双方同意，开始交往");
    }

    private void before() {

        System.out.println("我是媒婆，已经收集到你的需求，开始特色");
    }
}
