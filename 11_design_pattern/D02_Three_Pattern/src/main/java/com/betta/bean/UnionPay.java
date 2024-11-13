package com.betta.bean;

import org.springframework.stereotype.Component;

@Component
public class UnionPay extends AbstructPaymentStrategy {
    @Override
    public String pay(double amount) {
        return String.format("使用银联支付%f元", amount);
    }
}
