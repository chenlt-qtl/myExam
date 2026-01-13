package com.betta.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.不过滤的URL放行

        //3.获取token
        List<String> auths = request.getHeaders().get("authorization");
        if (Objects.isNull(auths) || auths.isEmpty()) {
            //拦截 设置响应状态码为401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //4.校验
        String authorization = auths.get(0);
        if ("okk".equals(authorization)) {
            //把用户信息放到header中
            exchange.mutate().request(builder -> builder.header("user-info","666"))
                    .build();
            return chain.filter(exchange);
        } else {
            //拦截 设置响应状态码为401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }
}
