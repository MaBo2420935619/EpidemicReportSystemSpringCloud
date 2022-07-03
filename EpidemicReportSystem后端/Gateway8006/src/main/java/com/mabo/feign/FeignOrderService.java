package com.mabo.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",path = "/user")
public interface FeignOrderService {
    @PostMapping("/login")
    JSONObject login(@RequestParam("uname") String uname, @RequestParam("upwd") String upwd,@RequestParam("type") String type);
}
