package com.seed.config;

import com.seed.bean.DogFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.seed.bean","com.seed.config"})
public class SpringConfig3 {

    @Bean
    //工厂方式创建Bean
    public DogFactoryBean dog(){
        return new DogFactoryBean();
    }
}
