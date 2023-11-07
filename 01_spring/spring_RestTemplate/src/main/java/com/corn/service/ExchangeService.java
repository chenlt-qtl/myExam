package com.corn.service;

import com.corn.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.url}")
    String url;

    /**
     * get
     */
    public Map exchangeGet() {
        System.out.println("**********exchange get*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        HttpEntity<String> entity = new HttpEntity(null, headers);

        ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        //getBody可以得到ResponseEntity<T>里面的响应消息体
        return result.getBody();
    }

    /**
     * post  传参使用MultiValueMap (API可以使用实体类(Student)接收 或者 基本类型分开接收)
     */
    public Map exchangePostEntity(String subUrl) {
        System.out.println("**********exchange post entity*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        //传参使用MultiValueMap (API使用包装类(Student)接收)
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "遥遥");
        paramMap.add("age", 18);

        HttpEntity<MultiValueMap> entity = new HttpEntity(paramMap, headers);

        ResponseEntity<Map> result = restTemplate.exchange(url + subUrl, HttpMethod.POST, entity, Map.class);

        return result.getBody();
    }


    /**
     * post  传参使用HttpEntity (API使用@RequestBody接收)
     */
    public Map exchangePostRequestBody() {
        System.out.println("**********exchange post requestBody*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        headers.setContentType(MediaType.APPLICATION_JSON);
        Student student = new Student("山鸡", 13);

        HttpEntity<Student> entity = new HttpEntity(student, headers);

        ResponseEntity<Map> result = restTemplate.exchange(url + "/body", HttpMethod.POST, entity, Map.class);

        return result.getBody();
    }


}
