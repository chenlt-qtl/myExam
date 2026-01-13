package com.betta.common.interceptor;

import com.betta.common.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取登录用户信息
        String userInfo = request.getHeader("user-info");
        log.info("****************得到用户ID：{} ",userInfo);
        //2. 判断是否获取了用户 如果有 存入ThreadLocal
        if(userInfo!=null && StringUtils.endsWithIgnoreCase(userInfo.trim(),"")){
            UserContext.setUser(Long.valueOf(userInfo));
        }
        //3. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
