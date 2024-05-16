package com.seed.service.impl;

import com.seed.service.IMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//@Service
public class MessageServiceImpl implements IMessageService {

    private ArrayList<String> msgList = new ArrayList<>();
    @Override
    public void sendMessage(String id) {
        msgList.add(id);
        System.out.println("订单已加入发送队列, id:"+id);
    }

    @Override
    public String doMessage() {
        String id = msgList.remove(0);
        System.out.println("订单已处理, id:"+id);
        return id ;
    }
}
