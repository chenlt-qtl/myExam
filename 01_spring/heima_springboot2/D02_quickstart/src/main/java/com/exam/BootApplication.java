package com.exam;

import com.exam.controller.BookController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 入门案例
 */
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(BootApplication.class, args);
        BookController bean = ctx.getBean(BookController.class);
        System.out.println("bean===>"+bean);
        System.out.println("启动完成");
    }
}
