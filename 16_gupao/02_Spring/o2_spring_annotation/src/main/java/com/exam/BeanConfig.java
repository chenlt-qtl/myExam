package com.exam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.exam")
public class BeanConfig {

    @Bean
    public Emp myEmp() {
        return new Emp("1","张三","男",20);
    }
}
