package com.seed.service.impl.kafka;

import com.seed.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sendMessage(String id) {
        kafkaTemplate.send("seed",id);
        System.out.println("订单已加入发送队列(KAFKA), id:"+id);
    }

    @Override
    public String doMessage() {
        return null;
    }
}
