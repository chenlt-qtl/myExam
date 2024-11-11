package com.betta.D04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Bean 生命周期
 */
@SpringBootApplication
public class D01 {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(D01.class);
        context.close();
    }
}
