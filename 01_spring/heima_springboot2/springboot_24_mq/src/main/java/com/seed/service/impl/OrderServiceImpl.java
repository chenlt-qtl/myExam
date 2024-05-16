package com.seed.service.impl;

import com.seed.service.IMessageService;
import com.seed.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IMessageService messageService;

    @Override
    public void order(String id) {
        System.out.println("订单处理开始");
        messageService.sendMessage(id);
        System.out.println("订单已发送到队列");
    }
}
