package com.teqgin.image_laboratory.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 连接redis，执行加载操作之前需要先进行连接
        template.setConnectionFactory(redisConnectionFactory);

        // 序列化配置
        var jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        var om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(om);

        // Sting的序列化配置
        var stringSerializer = new StringRedisSerializer();

        // key         采用String的序列化方式
        template.setKeySerializer(stringSerializer);
        // value       采用jackson序列化方式
        template.setValueSerializer(jacksonSerializer);
        // Hash的key   采用String的序列胡方式
        template.setHashKeySerializer(stringSerializer);
        // Hash的value 采用jackson序列化方式
        template.setHashValueSerializer(jacksonSerializer);

        // 加载所有设置,在连接之后加载
        template.afterPropertiesSet();

        return template;
    }
}