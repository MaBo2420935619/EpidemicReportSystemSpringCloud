package com.mabo.utils;

import java.util.Map;
import java.util.Random;

public class StringUtil {
    public static String getMapValueString(Map map, String key){
        if (map.get(key)!=null){
            return map.get(key).toString();
        }
        else return "";
    }
    public static String getBoolean(String booleanString){
        if (booleanString.equals("false")){
            return "否";
        }
        else if (booleanString.equals("true")){
            return "是";
        }
        else return "";
    }

    public static String doAppend(String string1,String string2) {
        StringBuilder sb = new StringBuilder();
        sb.append(string1);
        sb.append(string2);
        return sb.toString();
    }

    /**
     * 生成随机密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 32;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'm', 'n' , 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z' , '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            if (count==0){
                // 生成随机数，取绝对值，防止生成负数，
                i = Math.abs(r.nextInt(maxNum-8)); // 生成的数最大为36-1
                if (i >= 0 && i < str.length) {
                    pwd.append(str[i]);
                    count++;
                }
            }
            else {
                // 生成随机数，取绝对值，防止生成负数，
                i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
                if (i >= 0 && i < str.length) {
                    pwd.append(str[i]);
                    count++;
                }
            }

        }
        return pwd.toString();
    }

}
