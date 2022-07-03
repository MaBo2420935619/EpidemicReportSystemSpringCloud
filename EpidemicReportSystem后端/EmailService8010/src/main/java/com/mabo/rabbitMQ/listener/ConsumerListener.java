package com.mabo.rabbitMQ.listener;

import com.alibaba.fastjson.JSONObject;
import com.mabo.controller.SendEmailController;
import com.mabo.entity.Email;
import com.mabo.global.Result;
import com.mabo.service.EmailService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class ConsumerListener {
    public static Logger logger = LoggerFactory.getLogger(ConsumerListener.class);
   @Autowired
   EmailService emailService;
    @RabbitListener(queuesToDeclare = @Queue("dl_queue"))
    public void studentSendEmail(String message) {
        logger.info("message"+message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String address = jsonObject.getString("address");
        String subject = jsonObject.getString("subject");
        String content = jsonObject.getString("content");
        Email email = new Email(address, subject, content);
        emailService.sendEmail(email);
        logger.info("邮件发送成功：" + new Date());
    }

}
