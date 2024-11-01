package com.exam.D18_Thread.D02_Synchronized;

public class DrawThread extends Thread{

    private Account account;
    public DrawThread(String name, Account account) {
        super(name);
        this.account = account;
    }

    @Override
    public void run() {
        //取钱
        account.drawMoney(1000);
    }
}
