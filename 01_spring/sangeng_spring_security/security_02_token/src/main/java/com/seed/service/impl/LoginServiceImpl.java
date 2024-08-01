package com.seed.service.impl;

import com.seed.domain.LoginUser;
import com.seed.domain.SysUser;
import com.seed.service.ILoginService;
import com.seed.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String login(SysUser user) {
        //AuthenticationManager 进行用户认证
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);

        //如果认证没通过给出提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        //如果认证通过，生成JWT
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId();
        String jwt = JwtUtils.genJwt(id);

        //把完整的用户信息存入redis userId为KEY
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("login:"+id,loginUser);

        return jwt;
    }
}
