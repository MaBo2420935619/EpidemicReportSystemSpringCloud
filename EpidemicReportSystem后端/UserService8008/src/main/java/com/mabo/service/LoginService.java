package com.mabo.service;

import com.mabo.dao.LoginDao;

import com.mabo.entity.Login;

import com.mabo.utils.MD5Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Data
@Service
public class LoginService extends BaseService{
    @Autowired
    private LoginDao loginDao;

    /**
     * @Author mabo
     * @Description   判断是否登录
     */
    public  Boolean isLogin(String uname, String upwd,String type){
        upwd= MD5Util.getKey(upwd);
        Login login = new Login(uname, upwd, type);
        List<Login> logins = loginDao.queryLogin(login);
        if (logins.size()>0){
            return true;
        }
        else
            return false;
    }
}
