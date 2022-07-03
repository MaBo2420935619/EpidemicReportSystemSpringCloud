package com.mabo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
@Configuration
public class BeanInit{
    @Bean
    public RedisTemplate redisTemplate(@Autowired RedisTemplate redisTemplate){
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        //@bean方式将K设为String序列化后，V会自动转为String序列化。
        // 为了保持Object类型的V值，故我这边需将V设回默认的jdk的V，
        redisTemplate.setValueSerializer(redisTemplate.getDefaultSerializer());
        return redisTemplate;
    }
}

