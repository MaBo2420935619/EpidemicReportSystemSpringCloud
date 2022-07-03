package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.entity.Email;
import com.mabo.global.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
@Service
public class EmailService {
    public static Logger logger = LoggerFactory.getLogger(EmailService.class);

    //注入邮件发送对象
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUsername;

    public Result sendEmail(Email email){
        String address=email.getAddress();
        String subject=email.getSubject();
        String content=email.getContent();
        if ((address!=null||!address.equals(""))){
            if (subject==null){
                subject="无标题";
            }
            if (content==null){
                content="";
            }
            //创建简单的邮件发送对象
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUsername);           // 设置发件人邮箱（若配置默认邮箱则不用再设置）
            message.setTo(address);            // 设置收件人邮箱
//        message.setCc("xiaofeng500@qq.com");            // 设置抄报人邮箱（可以不填写）
//        message.setBcc("575814158@qq.com");             // 设置密送人邮箱（可以不填写）
            message.setSubject(subject);                  // 设置邮件主题
            message.setText(content); // 设置邮件文本内容
            message.setSentDate(new Date());                // 设置邮件发送时间
            //发送
            mailSender.send(message);
            String msg="邮件发送成功";
            logger.info(msg);
            JSONObject json=new JSONObject();
            json.put("address",address);
            json.put("subject",subject);
            json.put("content",content);
            json.put("msg",msg);
            return  Result.success(json) ;
        }
        else {
            String msg="邮件发送失败";
            logger.info(msg);
            JSONObject json=new JSONObject();
            json.put("reason","发送地址不正确");
            json.put("msg",msg);
            return  Result.error(json.toString());
        }
    }
}
