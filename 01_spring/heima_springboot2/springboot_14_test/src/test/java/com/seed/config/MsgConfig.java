package com.seed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class MsgConfig {

    @Bean
    public String msg(){
        return "bean msg";
    }
}
