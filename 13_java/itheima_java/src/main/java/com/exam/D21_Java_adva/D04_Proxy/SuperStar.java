package com.exam.D21_Java_adva.D04_Proxy;

public class SuperStar implements Star{
    private String name;

    public SuperStar(String name) {
        this.name = name;
    }

    @Override
    public String sing(String name){
        System.out.println(this.name+"正在唱"+name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "谢谢";
    }

    @Override
    public void dance(){
        System.out.println(this.name+"正在跳舞");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
