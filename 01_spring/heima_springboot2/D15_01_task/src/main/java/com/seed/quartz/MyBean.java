package com.seed.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @Scheduled(cron = "0/5 * * * * ?")
    public void print(){
        System.out.println("spring task run...");
        System.out.println(Thread.currentThread().getName());
    }
}
