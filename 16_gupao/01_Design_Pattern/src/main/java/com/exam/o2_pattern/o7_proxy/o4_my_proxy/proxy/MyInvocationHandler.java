package com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy;

import java.lang.reflect.Method;

public interface MyInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
