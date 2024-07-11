package com.betta.atm;

public class Account {
    private String id;
    private String name;
    private char sex;
    private String psd;
    private double money;

    private double limit;

    public Account() {
    }

    public Account(String id, String name, char sex, String psd, double money, double limit) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.psd = psd;
        this.money = money;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name + (sex == '男' ? "先生" : "女士");
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
