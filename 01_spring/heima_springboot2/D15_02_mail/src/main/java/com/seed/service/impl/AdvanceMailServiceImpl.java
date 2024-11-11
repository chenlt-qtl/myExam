package com.seed.service.impl;

import com.seed.service.ISendMailService;
import com.seed.service.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class AdvanceMailServiceImpl implements ISendMailService {


    @Autowired
    private JavaMailSender javaMailSender;
    private Mail mail = new Mail();
    {
        mail.setTo("113127542@qq.com");
        mail.setFrom("da.mu@163.com(小甜甜)");
        mail.setContent("<a harf='http://www.baidu.com'>百度</a><br/>" +
                "<img src='https://img2.baidu.com/it/u=3692729457,4065741174&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500'/>");
        mail.setSubject("高级邮件");
    }

//    @Override
    public void send() {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(),true);

            //添加附件
            File file = new File("C:\\Users\\Administrator\\Pictures\\桌面\\7.jpg");

            helper.addAttachment(file.getName(),file);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
