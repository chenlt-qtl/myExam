package com.seed.controller;

import com.seed.controller.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class JwtController {

    @PostMapping("/login")
    public String genJwt(HttpServletResponse response){
        String token = JwtUtils.genJwt();
        return token;
    }

    @GetMapping("/jwt")
    public String getJwt(){
        System.out.println("jwt 控制器");
        return "请求成功";
    }
}
