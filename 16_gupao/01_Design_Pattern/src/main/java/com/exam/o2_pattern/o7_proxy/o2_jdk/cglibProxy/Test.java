package com.exam.o2_pattern.o7_proxy.o2_jdk.cglibProxy;

public class Test {

    public static void main(String[] args) {
        Meipo meipo = new Meipo();
        IPerson zhangsan = meipo.getInstance(new Zhangsan());
        zhangsan.findLove();
        zhangsan.buyInsure();

        IPerson zhaoliu = meipo.getInstance(new Zhaoliu());
        zhaoliu.findLove();
        zhaoliu.buyInsure();
    }
}
