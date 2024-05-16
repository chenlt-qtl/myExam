package com.seed.service.impl.kafka.listener;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @KafkaListener(topics = "seed")
    public void doMessage(ConsumerRecord<String, String> record) {

        System.out.println("订单已由监听器处理(kafka), id:" + record.value());
    }
}
