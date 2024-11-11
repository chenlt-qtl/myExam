package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootApplication
public class SsmpApplication {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
//        自定义参数
        String[] myArg = new String[]{"--server.port=666"};
//        ConfigurableApplicationContext ctx = SpringApplication.run(SsmpApplication.class, myArg);
        ConfigurableApplicationContext ctx = SpringApplication.run(SsmpApplication.class, args);

//        为了安全性，不接收外部传进来的参数
//        ConfigurableApplicationContext ctx = SpringApplication.run(SsmpApplication.class);

    }
}
