package com.corn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.url}")
    String url;

    /**
     * 发送HTTP GET请求，ResponseEntity是对HTTP响应的封装，除了包含响应体，还包含HTTP状态码、contentType、contentLength、Header等信息
     * 参数 1. url
     * 2. 返回值的类型
     * 3. ？？
     */
    public<T> T getForEntity(Class<T> clazz) {
        System.out.println("**********getForEntity*************");
        ResponseEntity<T> forEntity = restTemplate.getForEntity(url + "?key=abc", clazz);
        return forEntity.getBody();
    }

    /**
     * 发送HTTP GET请求，返回映射的对象。
     */
    public<T> T getForObject(Class<T> clazz) {
        System.out.println("**********getForObject*************");
        return restTemplate.getForObject(url + "?key=abc", clazz);
    }
}
