package com.corn.service;

import com.corn.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.url}")
    String url;

    /**
     * 传参使用MultiValueMap (API可以使用实体类(Student)接收 或者 基本类型分开接收)
     */
    public <T> T postForEntity(String subUrl, Class<T> clazz) {
        System.out.println("**********postForEntity*************" + subUrl);

        //传参使用MultiValueMap (API使用包装类(Student)接收)
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "旺财");
        paramMap.add("age", 8);

        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url + subUrl, paramMap, clazz);
        //getBody可以得到ResponseEntity<T>里面的响应消息体
        return responseEntity.getBody();
    }

    /**
     * post  传参使用HttpEntity (API使用@RequestBody接收)
     */
    public <T> T postForObject( Class<T> clazz) {
        System.out.println("**********postForEntity*************");

        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        headers.setContentType(MediaType.APPLICATION_JSON);
        Student student = new Student("咪咪", 3);

        HttpEntity<Student> entity = new HttpEntity(student, headers);

        return restTemplate.postForObject(url + "/body", entity,clazz);
    }
}
