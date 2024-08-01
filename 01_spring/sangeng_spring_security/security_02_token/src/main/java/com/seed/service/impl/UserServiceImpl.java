package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.dao.UserDao;
import com.seed.domain.LoginUser;
import com.seed.domain.SysUser;
import com.seed.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

//实现security的UserDetailsService接口
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements IUserService, UserDetailsService {

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
}
