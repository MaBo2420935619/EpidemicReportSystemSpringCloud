package com.mabo.service;

import com.alibaba.fastjson.JSONObject;
import com.mabo.feign.FeignOrderService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * (Login)表服务实现类
 *
 * @author makejava
 * @since 2022-06-05 01:17:41
 */
@Service("loginService")
public class LoginService {
    @Resource
    private FeignOrderService feignOrderService;

    public JSONObject queryLogin(String uname,String upwd,String type) {
        JSONObject login1 = feignOrderService.login(uname, upwd,type);
        return login1;
    }


}
