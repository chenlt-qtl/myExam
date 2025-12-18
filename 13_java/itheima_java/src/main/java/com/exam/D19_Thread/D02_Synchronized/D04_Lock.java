package com.exam.D19_Thread.D02_Synchronized;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class D04_Lock {
    public static void main(String[] args) {
        //创建一个帐户对象，代表共有帐户
        Account account = new Account4("110", 1000);

        //创建两个线程，代表小明，小红，去同一个帐户对象中取钱
        new DrawThread("小明", account).start();
        new DrawThread("小红", account).start();
    }
}

class Account4 extends Account{

    //创建一个锁对象
    private final Lock lock = new ReentrantLock();

    public Account4(String cardId, double money) {
        super(cardId,money);
    }

    public void drawMoney(double money) {
        String name = Thread.currentThread().getName();
        System.out.println(name + "来取钱");

        try {
            lock.lock();
            if (this.money >= money) {
                System.out.println(name + "来取钱" + money + "成功");
                this.money -= money;
                System.out.println(name + "来取钱后，余额:" + this.money);
            } else {
                System.out.println(name + "来取钱，余额不足");
            }
        } finally {
            lock.unlock();
        }


    }
}

