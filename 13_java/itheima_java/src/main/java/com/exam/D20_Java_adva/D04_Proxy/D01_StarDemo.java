package com.betta.d42_proxy;

public class D01_StarDemo {
    public static void main(String[] args) {
        SuperStar eason = new SuperStar("陈奕迅");
        Star proxy = ProxyUtil.createProxy(eason);//分配经纪人
        System.out.println(proxy.sing("好日子"));
        proxy.dance();

    }
}
