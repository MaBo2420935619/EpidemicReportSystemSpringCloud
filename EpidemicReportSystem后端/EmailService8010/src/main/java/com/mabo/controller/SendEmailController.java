package com.mabo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mabo.entity.Email;
import com.mabo.global.GlobalBlockHandler;
import com.mabo.global.GlobalFallback;
import com.mabo.global.Result;
import com.mabo.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class SendEmailController {
    public static Logger logger = LoggerFactory.getLogger(SendEmailController.class);

    @Value("${spring.application.name}")
    private  String ServerName;

    @Value("${server.port}")
    private  String port;

    //注入邮件发送对象
    @Autowired
    private EmailService emailService;

    @RequestMapping("/sendMail")
    @SentinelResource(value = "sendMail",
            blockHandlerClass = GlobalBlockHandler.class,
            blockHandler = "globalBlockHandler",
            fallbackClass = GlobalFallback.class,
            fallback = "globalFallback"
    )
    public Result sendMail(
            @RequestParam("address")String address,
            @RequestParam("subject")String subject,
            @RequestParam("content")String content
    )
    {
        Email email = new Email(address,subject,content);
        Result result = emailService.sendEmail(email);
        return result;
    }


}
