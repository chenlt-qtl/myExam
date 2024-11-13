package com.betta.bean;

public abstract class AbstructPaymentStrategy implements PaymentStrategy{
    public final String commonPay(double amount){
        check(amount);
        return pay(amount);
    }

    private void check(double amount) {
        if(amount<=0){
            throw new IllegalArgumentException("无效的金额");
        }
    }

}
