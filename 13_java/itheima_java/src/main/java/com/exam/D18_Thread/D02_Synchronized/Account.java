package com.exam.D18_Thread.D02_Synchronized;

public abstract class Account {
    protected double money;
    protected String cardId;

    public Account() {
    }

    public Account(String cardId, double money) {
        this.money = money;
        this.cardId = cardId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public abstract void drawMoney(double money);
}
