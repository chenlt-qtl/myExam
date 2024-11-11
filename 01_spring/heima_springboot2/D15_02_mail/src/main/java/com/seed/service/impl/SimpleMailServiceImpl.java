package com.seed.service.impl;

import com.seed.service.ISendMailService;
import com.seed.service.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service
public class SimpleMailServiceImpl implements ISendMailService {


//    @Autowired
    private JavaMailSender javaMailSender;

    private Mail mail = new Mail();

    {
        mail.setTo("113127542@qq.com");
        mail.setFrom("da.mu@163.com(小甜甜)");
        mail.setContent("test text");
        mail.setSubject("test sub");
    }

    @Override
    public void send() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        javaMailSender.send(message);
    }
}
