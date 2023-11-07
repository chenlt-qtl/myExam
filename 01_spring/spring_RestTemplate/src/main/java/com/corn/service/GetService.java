package com.corn.service;

import com.corn.entity.ResponseBean;
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
    public ResponseBean getForEntity() {
        System.out.println("**********getForEntity*************");
        ResponseEntity<ResponseBean> forEntity = restTemplate.getForEntity(url + "?key=abc", ResponseBean.class);
        return forEntity.getBody();
    }

    /**
     * 发送HTTP GET请求，返回映射的对象。
     */
    public ResponseBean getForObject() {
        System.out.println("**********getForObject*************");
        return restTemplate.getForObject(url + "?key=abc", ResponseBean.class);
    }
}
