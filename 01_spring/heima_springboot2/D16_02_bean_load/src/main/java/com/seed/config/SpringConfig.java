package com.seed.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.seed.bean.Cat;
import com.seed.bean.Mouse;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@Import(MyImportSelector.class)
//@Import(Mouse.class)
@ComponentScan("com.seed.bean")
public class SpringConfig {

//    @Bean
//    @ConditionalOnClass(name = "com.seed.bean.Wolf")
//    @ConditionalOnMissingClass({"com.seed.bean.Wolf"})
//    @ConditionalOnBean(name = "com.seed.bean.Mouse")
//    @ConditionalOnBean(name = "jerry")
//    @ConditionalOnWebApplication //是web项目
//    @ConditionalOnNotWebApplication //不是web项目
//    public Cat tom() {
//        return new Cat();
//    }

    @Bean
    @ConditionalOnClass(name = "com.mysql.jdbc.Driver")//有数据库驱动才加载这个Bean
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

}
