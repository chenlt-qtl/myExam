package com.exam.D21_Java_adva.D04_Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 经纪公司
 */
public class ProxyUtil {

    public static Star createProxy(SuperStar superStar) {
        /**
         * 参数1：用于指定一个类加载器
         * 参数2：指定生成的代理有哪些方法
         * 参数3：指定生成的代理对象要干什么事情
         */
        Star starProxy = (Star) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{Star.class}, new InvocationHandler() {
                    @Override//回调方法
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("sing")) {
                            System.out.println("准备话筒，收钱20W");
                        } else if (method.getName().equals("dance")) {
                            System.out.println("准备场地，收钱100W");
                        }
                        return method.invoke(superStar, args);
                    }
                });
        return starProxy;
    }
}
