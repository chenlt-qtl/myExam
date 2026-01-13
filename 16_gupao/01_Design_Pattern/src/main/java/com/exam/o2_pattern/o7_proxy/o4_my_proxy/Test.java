package com.exam.o2_pattern.o7_proxy.o4_my_proxy;


public class Test {

    public static void main(String[] args) {
        Meipo meipo = new Meipo();
        IPerson zhangsan = meipo.getInstance(new Zhangsan());
        zhangsan.findLove();
        zhangsan.buyInsure();
    }
}
