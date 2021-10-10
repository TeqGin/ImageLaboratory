package com.teqgin.image_laboratory.redisTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teqgin.image_laboratory.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void connect(){
        // redisTemplate
        // opsForValue 操作字符串
        // opsForList  操作列表
    }

    @Test
    public void SerializeTest() throws JsonProcessingException {
        var user = new User();
        redisTemplate.opsForValue().set("user", user);
    }
}
