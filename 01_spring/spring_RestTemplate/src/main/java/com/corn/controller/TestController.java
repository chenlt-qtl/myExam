package com.corn.controller;


import com.corn.entity.ResponseBean;
import com.corn.service.ExchangeService;
import com.corn.service.GetService;
import com.corn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseBean exchange() {
        //参数
        Map<String, Object> param = new HashMap<>();
        param.put("key", "你");
        ResponseBean response = exchangeService.exchangeGet(ResponseBean.class, param);
//        ResponseBean response = exchangeService.exchangePostRequestBody(ResponseBean.class);
//        ResponseBean response = exchangeService.exchangePostEntity("/entity",ResponseBean.class);
//        ResponseBean response = exchangeService.exchangePostEntity("/basic", ResponseBean.class);
        return response;
    }

    @GetMapping("/get")
    public ResponseBean get() {

        ResponseBean map = getService.getForEntity(ResponseBean.class);
        map = getService.getForObject(ResponseBean.class);
        return map;
    }

    @GetMapping("/post")
    public ResponseBean post() {

//        ResponseBean map = postService.postForEntity("/basic");
//        ResponseBean map = postService.postForEntity("/entity");
        ResponseBean map = postService.postForObject(ResponseBean.class);
        return map;
    }
}
