package com.seed;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

//@SpringBootTest
class Security03Token2ApplicationTests {

    @Test
    void contextLoads() {

        System.out.println(StringUtils.hasText(""));
        System.out.println(StringUtils.hasText(" "));
        System.out.println(StringUtils.hasText(null));
        System.out.println(StringUtils.hasText("  "));
        System.out.println(StringUtils.hasText("1"));

    }

}
