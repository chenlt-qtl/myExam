package com.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class Springboot30IpStarterApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Springboot30IpStarterApplication.class, args);
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }

}
