package com.seed.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CodeUtils {

    public String generator(String tel) {
        int hash = tel.hashCode();
        int encryption = 2020666;
        long result = hash ^ encryption;
        long nowTime = System.currentTimeMillis();
        result = result ^ nowTime;
        result = result < 0 ? -result : result;
        long code = result % 1000000;
        String str = String.valueOf(code);
        return Strings.repeat("0", 6 - str.length()) + str;
    }


    public static void main(String[] args) {
        String code = new CodeUtils().generator("14300223020");
        System.out.println(code);
    }
}
