package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.dao.UserDao;
import com.seed.domain.LoginUser;
import com.seed.domain.SysUser;
import com.seed.service.IUserService;
import com.seed.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

//实现security的UserDetailsService接口
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements IUserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUsername,s );
        SysUser user = getOne(queryWrapper);
        //没有找到用户抛异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }else{
            //把数据封闭成userDetail返回
            return new LoginUser(user);
        }

    }

    @Override
    public String login(SysUser user) {
        String token;
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        if(!passwordEncoder.matches(user.getPassword(),userDetails.getPassword())){
            throw new RuntimeException("密码不正确");
        }
        if(!userDetails.isEnabled()){
            throw new RuntimeException("帐号已被禁用");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        token = jwtUtils.genJwt(userDetails.getUsername());

        return token;
    }
}
