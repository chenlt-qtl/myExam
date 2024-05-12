package com.seed;

import com.seed.config.MsgConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MsgConfig.class)
public class ConfigurationTest {

    @Autowired
    private String msg;


    @Test
    public void test(){
        System.out.println(msg);
    }
}
