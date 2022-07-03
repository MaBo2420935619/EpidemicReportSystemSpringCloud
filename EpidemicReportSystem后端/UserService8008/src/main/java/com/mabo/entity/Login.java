package com.mabo.entity;

import org.apache.ibatis.annotations.Mapper;
import java.io.Serializable;

/**
 * (Login)实体类
 *
 * @author makejava
 * @since 2022-06-05 00:53:38
 */
@Mapper
public class Login implements Serializable {
    private static final long serialVersionUID = -31121479323162928L;

    private String uname;

    private String upwd;

    private String type;

    public Login() {
    }

    public Login(String uname, String upwd, String type) {
        this.uname = uname;
        this.upwd = upwd;
        this.type = type;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

