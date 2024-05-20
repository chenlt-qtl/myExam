package com.seed.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyListener implements ApplicationListener<ApplicationStartedEvent> {
    public void onApplicationEvent(ApplicationStartedEvent applicationEvent) {
        System.out.println("=======================");
        System.out.println(applicationEvent.getSource());
        System.out.println(applicationEvent.getClass());
    }
}
