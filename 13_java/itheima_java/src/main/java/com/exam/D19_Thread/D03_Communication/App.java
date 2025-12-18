package com.exam.D19_Thread.D03_Communication;

/**
 * 线程通信示例
 * 一张桌子
 * 厨师一次只能做一个包子 等客户消费完才能再做一个
 */
public class App {
    public static void main(String[] args) {
        Desk desk = new Desk();
        new Cooker(desk,"厨师一").start();
        new Cooker(desk,"厨师二").start();
        new Cooker(desk,"厨师三").start();
        new Eater(desk,"吃货一").start();
        new Eater(desk,"吃货二").start();
    }
}

