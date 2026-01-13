package com.exam.o2_pattern.o7_proxy.o4_my_proxy;
import com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy.MyClassLoader;
import com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy.MyInvocationHandler;
import com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy.MyProxy;

import java.lang.reflect.Method;


public class Meipo implements MyInvocationHandler {
    private IPerson person;
    public IPerson getInstance(IPerson person) {
        this.person = person;
        Class clazz = IPerson.class;
        return (IPerson) MyProxy.newProxyInstance(new MyClassLoader(), new Class[]{clazz},this);
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
