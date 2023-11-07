package com.corn.controller;


import com.corn.entity.ResponseBean;
import com.corn.service.ExchangeService;
import com.corn.service.GetService;
import com.corn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    GetService getService;

    @Autowired
    PostService postService;

    @GetMapping("/exchange")
    public String exchange() {
//        Map map = exchangeService.exchangeGet();
//        Map map = exchangeService.exchangePostRequestBody();
//        Map map = exchangeService.exchangePostEntity("/entity");
        Map map = exchangeService.exchangePostEntity("/basic");
        return map.toString();
    }

    @GetMapping("/get")
    public ResponseBean get() {

        ResponseBean map = getService.getForEntity();
//        ResponseBean map = getService.getForObject();
        return map;
    }

    @GetMapping("/post")
    public ResponseBean post() {

//        ResponseBean map = postService.postForEntity("/basic");
//        ResponseBean map = postService.postForEntity("/entity");
        ResponseBean map = postService.postForObject();
        return map;
    }
}
