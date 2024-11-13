package com.betta.bean;

import org.springframework.stereotype.Component;

@Component
public class AliPay extends AbstructPaymentStrategy {
    @Override
    public String pay(double amount) {
        return String.format("使用支付宝支付%f元", amount);
    }
}