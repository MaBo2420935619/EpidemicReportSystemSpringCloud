package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.LoginService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author mabo
 * @Description   在这里写请求方法
 */
@Data
@RestController
@RequestMapping("/user")
public class LoginController{
    @Autowired
    private LoginService loginService;

private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public JSONObject login(@RequestParam("uname")String uname, @RequestParam("upwd")String upwd, @RequestParam("type")String type){
        Boolean logins = loginService.isLogin( uname,  upwd, type);
        Boolean login = false;
        if (logins){
            login=true;
        }
        JSONObject json=new JSONObject();
        if (login) {
            json.put("message","true");
            Map<String, String> info = new HashMap<>();
            info.put("username", uname);
            json.put("200", "登录成功");
            json.put("info", info);
        } else {
            json.put("message","false");
            json.put("401", "登录失败");
            json.put("info", "用户名或密码错误");
        }
        log.info("登录结果"+json);
        return json;
    }

}
