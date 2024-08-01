package com.seed.controller;

import com.seed.domain.ResultBean;
import com.seed.domain.SysUser;
import com.seed.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    IUserService userService;

    @PostMapping
    public ResultBean login(@RequestBody SysUser user) {

        String token = userService.login(user);

//        String jwt =  loginService.login(user);

//        return ResultBean.success(jwt);
        return ResultBean.success(token);
    }
}
