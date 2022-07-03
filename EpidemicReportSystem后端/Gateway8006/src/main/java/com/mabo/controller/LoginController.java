package com.mabo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mabo.service.LoginService;
import com.mabo.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("user")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 服务对象
     */
    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    public JSONObject login(@RequestParam("uname")String uname,@RequestParam("upwd")String upwd,@RequestParam("type")String type){
        JSONObject jsonObject = loginService.queryLogin(uname,upwd,type);

        Boolean login = false;
        if (jsonObject.getBoolean("message")){
            login=true;
        }
        JSONObject json=new JSONObject();
        if (login) {
            json.put("message","true");
            Map<String, String> info = new HashMap<>();
            info.put("username", uname);
            String token = JWTUtil.createJWT(UUID.randomUUID().toString(),upwd,
                    null);
            json.put("token", token);
            json.put("200", "登录成功");
            json.put("info", info);
        } else {
            json.put("message","false");
            json.put("401", "登录失败");
            json.put("info", "用户名或密码错误");
        }
        return json;
    }
}


