package com.seed.controller.interceptor;

import com.seed.controller.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    @Override //目标方法执行前执行 返回true放行  返回false不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)){
            log.info("请求头token为空");
            response.getWriter().write("未登录");
            return false;
        }else{
            try {
                JwtUtils.parseJwt(token);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("解析令牌失败");
                response.getWriter().write("未登录");
                return false;
            }
            return true;
        }
    }

    @Override //目标方法执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override //视图渲染完毕后执行，最后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
