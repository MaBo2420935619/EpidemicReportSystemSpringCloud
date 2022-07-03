package com.mabo.global;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;

public class GlobalBlockHandler {
    public static Result globalBlockHandler(String address, String subject, String content, BlockException e){
        String msg=" 服务器忙，请稍后重试BlockHandler"+e;
        JSONObject json=new JSONObject();
        json.put("address",address);
        json.put("subject",subject);
        json.put("content",content);
        json.put("msg",msg);
        return  Result.error(json);
    }
}
