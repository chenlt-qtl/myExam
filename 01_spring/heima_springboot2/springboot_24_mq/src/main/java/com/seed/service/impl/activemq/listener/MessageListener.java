package com.seed.service.impl.activemq.listener;

import com.seed.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

//@Component
public class MessageListener {


    @JmsListener(destination = "order.queue.id")
    //返回值发送到order.queue.id.2队列
    @SendTo("order.queue.id.2")
    public String receive(String id) {
        System.out.println("订单已由监听器处理, id:" + id);
        return "new" + id;
    }
}
