package com.mabo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RabbitMQ-service",path = "/MQ")
public interface FeignEmailService {
    @RequestMapping("/message")
    String sendMail( @RequestParam("address")String address,
                   @RequestParam("subject")String subject,
                   @RequestParam("content")String content);
}
