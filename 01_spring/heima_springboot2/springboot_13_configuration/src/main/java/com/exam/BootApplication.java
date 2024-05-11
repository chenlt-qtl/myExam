package com.exam;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(MyDataSource.class)
public class BootApplication {

    @Bean
    @ConfigurationProperties(prefix = "my-druid")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BootApplication.class, args);
        MyDataSource bean = ctx.getBean(MyDataSource.class);
        System.out.println(bean);
        DruidDataSource bean1 = ctx.getBean(DruidDataSource.class);
        System.out.println(bean1.getDriverClassName());
    }
}
