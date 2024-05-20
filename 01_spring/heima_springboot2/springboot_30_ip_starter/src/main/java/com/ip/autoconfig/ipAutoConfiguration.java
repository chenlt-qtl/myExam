package com.ip.autoconfig;

import com.ip.IpCountInterceptor;
import com.ip.IpCountService;
import com.ip.SpringMvcConfig;
import com.ip.properties.IpProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//@EnableConfigurationProperties(IpProperties.class)
@Import({IpProperties.class, SpringMvcConfig.class})
public class ipAutoConfiguration {

    @Bean
    public IpCountService ipCountService(){
        return new IpCountService();
    }
}
