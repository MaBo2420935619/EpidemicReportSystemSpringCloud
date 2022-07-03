package com.mabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmailServiceApplication8010 {
    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication8010.class, args);
    }
}
