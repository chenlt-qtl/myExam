package com.seed.controller.filter;

import com.seed.controller.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
@Slf4j
public class JWTFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化。。。");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器拦截到了请求，放行前执行");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        log.info("请求的URL: {}", url);

        if(url.contains("login")){
            log.info("登录操作");
            filterChain.doFilter(request,response);
        }else {
            String token = request.getHeader("token");
            if(!StringUtils.hasLength(token)){
                log.info("请求头token为空");
                response.getWriter().write("未登录");
            }else{
                try {
                    JwtUtils.parseJwt(token);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("解析令牌失败");
                    response.getWriter().write("未登录");
                    return;
                }
                filterChain.doFilter(request,response);
            }
        }

       System.out.println("过滤器拦截到了请求，放行后执行");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁。。。");
    }
}
