package com.mabo.global;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;

public class GlobalBlockHandler {
    public static Result globalBlockHandler( BlockException e){
        String msg=" 服务器忙，请稍后重试BlockHandler"+e;
        JSONObject json=new JSONObject();
        json.put("msg",msg);
        return  Result.error(json);
    }
}
