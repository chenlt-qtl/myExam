package com.betta.controller;

import com.betta.bean.AbstructPaymentStrategy;
import com.betta.bean.PaymentStrategy;
import com.betta.factory.PaymentStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    PaymentStrategyFactory factory;

    //请求地址 /pay?payType=1&amount=10
    @RequestMapping("pay")
    public String pay(int payType,double amount){
        AbstructPaymentStrategy paymentStrategy = factory.getPaymentStrategy(payType);
        return paymentStrategy.commonPay(amount);
    }
}
