package com.seed;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.io.InputStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//开启虚拟MVC调用
@AutoConfigureMockMvc
public class WebTest {

    @Test
    public void testWeb(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/articles");
        mvc.perform(builder);
    }

    @Test
    public void testStatus(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/articles");
        ResultActions result = mvc.perform(builder);
        //定论本次测试预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();

        //将预期和真实值进行匹配
        result.andExpect(ok);
    }

    @Test
    public void testBody(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/articles");
        ResultActions result = mvc.perform(builder);
        //定论本次测试预期值
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher resultMatcher = content.string("getById");

        //将预期和真实值进行匹配
        result.andExpect(resultMatcher);

        Resource resource = new ClassPathResource("beanFactoryTest.xml");
        InputStream is = resource.getInputStream();
    }

    @Test
    public void testJson(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/articles/1");
        ResultActions result = mvc.perform(builder);
        //定论本次测试预期值
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher resultMatcher = content.json("{\"title\":\"title\",\"id\":1,\"comment\":\"comment\"}");

        //将预期和真实值进行匹配
        result.andExpect(resultMatcher);
    }

    @Test
    public void testContentType(@Autowired MockMvc mvc) throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.get("/articles");
        ResultActions result = mvc.perform(builder);
        //定论本次测试预期值
        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher matcher = header.string("Content-Type", "application/json");
        //将预期和真实值进行匹配
        result.andExpect(matcher);
    }
}
