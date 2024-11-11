package com.seed;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class Springboot16RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void set() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("age",41);
    }

    @Test
    void get() {
        ValueOperations ops = redisTemplate.opsForValue();
        Object age = ops.get("age");
        System.out.println(age);
    }

    @Test
    void hset() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("info","a","1");
    }

    @Test
    void hget() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        System.out.println(hashOperations.get("info", "a"));
    }

    @Test
    void getString() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String a = ops.get("a");
        System.out.println(a);
    }

}
