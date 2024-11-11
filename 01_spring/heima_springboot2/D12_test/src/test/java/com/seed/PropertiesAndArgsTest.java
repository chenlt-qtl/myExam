package com.seed;


import com.seed.domain.MyDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest(properties = {"test.prop=test11"})
@SpringBootTest(args = "--test.prop=test22",properties = {"test.prop=test11"})
public class PropertiesAndArgsTest {

    @Value("${test.prop}")
    private String value;

    @Autowired
    private MyDomain myDomain;

    @Test
    void testProperties(){
        System.out.println(value);
        System.out.println(myDomain);
    }
}
