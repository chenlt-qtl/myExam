package com.seed;

import com.seed.service.ISendMailService;
import com.seed.service.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot23MailApplicationTests {

    @Autowired
    private ISendMailService sendMailService;

    @Test
    void contextLoads() {
        sendMailService.send();
    }

}
