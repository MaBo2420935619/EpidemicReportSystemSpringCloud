package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.*;

@Component
public class EmailRabbitMQService {
    public static Logger log = LoggerFactory.getLogger(EmailRabbitMQService.class);

    //注入邮件发送对象
    @Autowired
    private EmailService emailService;

    @RabbitListener(queuesToDeclare = @Queue("email_order"))
    public void process(String message) {
        log.info("queuesToDeclare-email_order:"+message);
        JSONObject json = (JSONObject) JSONObject.parse(message);
        Email email = new Email(json.getString("address"),json.getString("subject"),json.getString("content"));
        emailService.sendEmail(email);
    }

}
