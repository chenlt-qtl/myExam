package com.betta.api.config;

import com.betta.api.client.ItemClient;
import com.betta.api.fallback.ItemClientFallback;
import com.betta.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor userInterceptor(){
        //将用户信息添加到header
        return requestTemplate -> requestTemplate.header("user-info", String.valueOf(UserContext.getUser()));
    }

    @Bean
    public ItemClientFallback itemClientFallback(){
        return new ItemClientFallback();
    }
}
