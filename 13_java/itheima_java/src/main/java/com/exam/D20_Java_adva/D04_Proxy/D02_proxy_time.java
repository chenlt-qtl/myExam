package com.betta.d42_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 使用代理记录执行时间
 */
public class D02_proxy_time {

    public static void main(String[] args) {
        SuperStar eason = new SuperStar("陈奕迅");
        Star proxy = createProxy(eason);//分配经纪人
        System.out.println(proxy.sing("好日子"));
        proxy.dance();
    }
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
                        long startTime = System.currentTimeMillis();

                        Object result = method.invoke(superStar, args);
                        long endTime = System.currentTimeMillis();

                        System.out.println(method.getName() + "方法执行耗时：" + (endTime - startTime) / 1000.0 + "秒");

                        return result;
                    }
                });
        return starProxy;
    }
}
