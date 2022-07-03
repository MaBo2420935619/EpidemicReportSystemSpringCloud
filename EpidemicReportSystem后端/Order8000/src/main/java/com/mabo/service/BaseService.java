package com.mabo.service;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
@Data
public class BaseService {
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public JdbcTemplate jdbcTemplate;

}
