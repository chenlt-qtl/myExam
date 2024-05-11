package com.seed;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest//(properties = {})
public class PropertiesAndArgsTest {

    @Value("${test.prop}")
    private String value;

    @Test
    void testProperties(){
        System.out.println(value);
    }
}
