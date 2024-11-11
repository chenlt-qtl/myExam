package com.seed;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seed.domain.SysUser;
import com.seed.service.IUserService;
import com.seed.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Security02TokenApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void testPasswordEncoder() {
        String encode = passwordEncoder.encode("1234");//加密
        String encode1 = passwordEncoder.encode("1234");//加密
        System.out.println(encode1);
        System.out.println(encode);

        //校验
        System.out.println(passwordEncoder.matches("shmily@123", "0c7e322100782701"));


    }

    @Test
    void testGet(){
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUsername,"damu" );
        SysUser one = userService.getOne(queryWrapper);
        System.out.println(one);
    }

    @Test
    void testJWT(){
        String s = JwtUtils.genJwt("abc");
        System.out.println(JwtUtils.parseJwt(s));
    }

}
