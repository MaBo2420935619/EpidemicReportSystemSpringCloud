package com.mabo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service",path = "/stock")
public interface FeignStockService {
    @RequestMapping("/reduce")
    String reduce(@RequestParam("time") String time);
}
