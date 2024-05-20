package com.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class Springboot30StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot30StartupApplication.class, args);
        Properties properties = System.getProperties();
        properties.list(System.out);
    }


}
