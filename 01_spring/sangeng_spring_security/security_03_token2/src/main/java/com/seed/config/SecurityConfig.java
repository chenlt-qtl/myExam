package com.seed.config;

import com.seed.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }


    @Bean//注册一个PasswordEncoder对象，security会自动使用这个加密密码
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //不需要保护的资源放到允许访问

        IgnoreUrlsConfig ignoreUrlsConfig = ignoreUrlsConfig();

        for (String url : ignoreUrlsConfig.getUrls()) {
            http.authorizeRequests().antMatchers(url).permitAll();
        }

        //允许跨域请求的option请求
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();

        //其他请求
        http.authorizeRequests().anyRequest().authenticated();

        //关闭csrf
        http.csrf().disable();

        //不通过session获取securityContext
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //加入jwt过滤器
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
