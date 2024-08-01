package com.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
public class Security01QuickstartApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Security01QuickstartApplication.class, args);
        DefaultSecurityFilterChain bean = run.getBean(DefaultSecurityFilterChain.class);
        bean.getFilters().forEach(filter -> System.out.println(filter));
    }

}
