package com.betta.factory;

import com.betta.bean.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public AbstructPaymentStrategy getPaymentStrategy(int payType){
        switch (payType){
            case 1:
                return applicationContext.getBean(AliPay.class);
            case 2:
                return applicationContext.getBean(WeChatPay.class);
            case 3:
                return applicationContext.getBean(UnionPay.class);
            default:
                throw new IllegalArgumentException("支付类型错误");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
