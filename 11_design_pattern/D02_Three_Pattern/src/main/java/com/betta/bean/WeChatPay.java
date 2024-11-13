package com.betta.bean;

import org.springframework.stereotype.Component;

@Component
public class WeChatPay extends AbstructPaymentStrategy {
    @Override
    public String pay(double amount) {
        return String.format("使用微信支付%f元", amount);
    }
}