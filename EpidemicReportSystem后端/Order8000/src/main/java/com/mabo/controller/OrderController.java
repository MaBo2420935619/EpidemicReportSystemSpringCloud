package com.mabo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import com.mabo.feign.FeignStockService;
import com.mabo.global.GlobalBlockHandler;
import com.mabo.global.GlobalFallback;
import com.mabo.global.Result;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    public static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    FeignStockService stockService;

    @Value("${spring.application.name}")
    private  String ServerName;

    @Value("${server.port}")
    private  String port;


//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Trace
    @RequestMapping("/add")
    @SentinelResource(value = "add",
            blockHandlerClass = GlobalBlockHandler.class,
            blockHandler = "orderException",
            fallbackClass = GlobalFallback.class,
            fallback = "globalFallback"
    )
    public Result add() {

        Date date = new Date();
        long time = date.getTime();
        String sql="INSERT INTO orders (name, id) VALUES (?, ?)";
//        int update = jdbcTemplate.update(sql, time, time);
//        logger.info(" update"+update);
//        int a=1/0;
//        String msg = stockService.reduce(String.valueOf(time));
//        logger.info(msg);
        JSONObject json=new JSONObject();
        json.put("serverIp",ServerName+":"+port);
//        json.put("msg",msg);
        return  Result.success(json) ;
    }


}
