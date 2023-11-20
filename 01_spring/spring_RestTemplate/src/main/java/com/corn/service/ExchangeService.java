package com.corn.service;

import com.corn.entity.ResponseBean;
import com.corn.entity.Student;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public <T> T exchangeGet(Class<T> clazz, Map<String, Object> param) {
        System.out.println("**********exchange get*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        HttpEntity<String> entity = new HttpEntity(null, headers);


        //get url已编码，直接传递URI,以避免restTemplate的再次编码
        URI uri = URI.create(parseUrl(url, param));

        ResponseEntity<T> result = restTemplate.exchange(uri, HttpMethod.GET, entity, clazz);

        //getBody可以得到ResponseEntity<T>里面的响应消息体
        return result.getBody();
    }

    /**
     * post  传参使用MultiValueMap (API可以使用实体类(Student)接收 或者 基本类型分开接收)
     */
    public <T> T exchangePostEntity(String subUrl, Class<T> clazz) {
        System.out.println("**********exchange post entity*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        //传参使用MultiValueMap (API使用包装类(Student)接收)
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "遥遥");
        paramMap.add("age", 18);

        HttpEntity<MultiValueMap> entity = new HttpEntity(paramMap, headers);

        ResponseEntity<T> result = restTemplate.exchange(url + subUrl, HttpMethod.POST, entity, clazz);

        return result.getBody();
    }


    /**
     * post  传参使用HttpEntity (API使用@RequestBody接收)
     */
    public <T> T exchangePostRequestBody(Class<T> clazz) {
        System.out.println("**********exchange post requestBody*************");

        //头部
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "Jack");

        headers.setContentType(MediaType.APPLICATION_JSON);
        Student student = new Student("山鸡", 13);

        HttpEntity<Student> entity = new HttpEntity(student, headers);

        ResponseEntity<T> result = restTemplate.exchange(url + "/body", HttpMethod.POST, entity, clazz);

        return result.getBody();
    }

    public String parseUrl(String url, Map<String, ?> params) {
        if (null == params || params.size() <= 0) {
            return url;
        }
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, ?> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return url + "?" + URLEncodedUtils.format(pairs, "UTF-8");
    }

}
