package com.seed.service.impl.activemq;

import com.seed.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

//@Service
public class MessageServiceImpl implements IMessageService {

//    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(String id) {
        messagingTemplate.convertAndSend("order.queue.id",id);
        System.out.println("订单已加入发送队列, id:"+id);
    }

    @Override
    public String doMessage() {
        String id = messagingTemplate.receiveAndConvert("order.queue.id",String.class);
        System.out.println("订单已处理, id:"+id);
        return id ;
    }
}
