package com.mabo.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
@Data
@Component
public class InitProject implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(InitProject.class);
    @Autowired
    private  RedisTemplate redisTemplate;
    @Override
    public void run(String... args) {
        log.info("==========init project===========");
        log.info("开始初始化redis缓存开始");
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        log.info("开始初始化redis结束");
    }
}

