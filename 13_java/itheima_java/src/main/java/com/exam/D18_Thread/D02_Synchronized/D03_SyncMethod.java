package com.exam.D18_Thread.D02_Synchronized;

public class D03_SyncMethod {
    public static void main(String[] args) {
        //创建一个帐户对象，代表共有帐户
        Account account = new Account3("110", 1000);

        //创建两个线程，代表小明，小红，去同一个帐户对象中取钱
        new DrawThread("小明", account).start();
        new DrawThread("小红", account).start();
    }
}

class Account3 extends Account{


    public Account3(String cardId, double money) {
        super(cardId,money);
    }

    public synchronized void drawMoney(double money) {
        System.out.println("同步方法");
        String name = Thread.currentThread().getName();
        System.out.println(name + "来取钱");
        if (this.money >= money) {
            System.out.println(name + "来取钱" + money + "成功");
            this.money -= money;
            System.out.println(name + "来取钱后，余额:" + this.money);
        } else {
            System.out.println(name + "来取钱，余额不足");
        }


    }
}

