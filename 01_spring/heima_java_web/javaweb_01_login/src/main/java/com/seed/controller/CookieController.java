package com.seed.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cookie")
public class CookieController {


    @PostMapping
    public String setCookie(HttpServletResponse response){
        response.addCookie(new Cookie("abc","324"));
        return "success";
    }

    @GetMapping
    public String getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String value = "";
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("abc")){
                value = cookie.getValue();
            }
        }
        return value;
    }
}
