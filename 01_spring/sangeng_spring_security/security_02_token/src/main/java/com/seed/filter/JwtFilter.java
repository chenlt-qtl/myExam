package com.seed.filter;

import com.seed.domain.LoginUser;
import com.seed.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");

        if(!StringUtils.hasText(token)){
            //放行，让后面的过滤器校验
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //解析token
        String userId = JwtUtils.parseJwt(token);

        //从redis中获取用户信息
        ValueOperations ops = redisTemplate.opsForValue();
        Object obj = ops.get("login:" + userId);
        if(Objects.isNull(obj)){
           throw new RuntimeException("用户未登录");

        }
        LoginUser loginUser = (LoginUser) obj;
        //存入securityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //确定是否放行
    }
}
