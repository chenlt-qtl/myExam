package com.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan//开启了对servler组件的支持
public class Javaweb01LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(Javaweb01LoginApplication.class, args);
    }

}
