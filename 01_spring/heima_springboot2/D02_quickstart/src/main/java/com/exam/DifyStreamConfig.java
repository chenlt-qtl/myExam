package com.exam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DifyStreamConfig {

    @Bean
    public WebClient difyWebClient() {
        return WebClient.builder()
                .baseUrl("http://121.199.38.74:8888/v1") // Dify API基础地址
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer app-Rcon7MjzOyW6FgKZp4OVSFPE") // 替换为你的Dify API Key
                .build();
    }
}
