package com.mabo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@MapperScan("com.mabo.dao")
@SpringBootApplication
@EnableFeignClients
public class OrderApplication8000 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8000.class, args);
    }
}
