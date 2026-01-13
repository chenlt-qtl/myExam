package com.exam.o2_pattern.o7_proxy.o2_jdk.cglibProxy;

public class Zhaoliu implements IPerson{
    @Override
    public void findLove() {
        System.out.println("赵六要求：有车有房学历高");
    }

    @Override
    public void buyInsure() {
        System.out.println("赵六买100W");
    }
}
