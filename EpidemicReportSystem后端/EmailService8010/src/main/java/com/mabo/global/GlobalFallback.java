package com.mabo.global;

import com.alibaba.fastjson.JSONObject;

public class GlobalFallback {

    public static Result globalFallback(String address, String subject, String content, Throwable e){
        String msg=" 服务器崩溃，无法正常访问Fallback"+e;
        JSONObject json=new JSONObject();
        json.put("address",address);
        json.put("subject",subject);
        json.put("content",content);
        json.put("msg",msg);
        return  Result.error(json);
    }
}
