package com.mabo.global;

import com.alibaba.fastjson.JSONObject;

public class GlobalFallback {

    public static Result globalFallback( Throwable e){
        String msg=" 服务器崩溃，无法正常访问Fallback"+e;
        JSONObject json=new JSONObject();
        json.put("msg",msg);
//        return  Result.success(json) ;
        return  Result.error(json);
    }
}
