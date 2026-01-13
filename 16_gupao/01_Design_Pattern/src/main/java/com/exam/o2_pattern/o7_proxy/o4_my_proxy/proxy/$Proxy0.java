package com.exam.o2_pattern.o7_proxy.o4_my_proxy.proxy;

import java.lang.reflect.Method;

public class $Proxy0 implements com.exam.o2_pattern.o7_proxy.o4_my_proxy.IPerson {
    MyInvocationHandler h;

    public $Proxy0(MyInvocationHandler h) {
        this.h = h;
    }

    public void buyInsure() {
        try {
            Method method = com.exam.o2_pattern.o7_proxy.o4_my_proxy.IPerson.class.getMethod("buyInsure", new Class[]{});
            Object object = h.invoke(this, method, new Object[]{});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

    public void findLove() {
        try {
            Method method = com.exam.o2_pattern.o7_proxy.o4_my_proxy.IPerson.class.getMethod("findLove", new Class[]{});
            Object object = h.invoke(this, method, new Object[]{});
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }
}