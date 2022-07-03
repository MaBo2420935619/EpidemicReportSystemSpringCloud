package com.mabo.rabbitMQ.sender;

import com.alibaba.fastjson.JSON;
import com.mabo.config.RabbitmqConfig;
import com.mabo.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQEmailSender {
    public static Logger log = LoggerFactory.getLogger(MQEmailSender.class);

    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;


    //发送消息方法调用: 构建Message消息
    public void send(String address,String title,String content) {
        Email email = new Email(address,title,content);
        String s = JSON.toJSONString(email);
        rabbitTemplate.convertAndSend(RabbitmqConfig.TTL_EXCHANGE, "ttl.order", s);
    }
}
