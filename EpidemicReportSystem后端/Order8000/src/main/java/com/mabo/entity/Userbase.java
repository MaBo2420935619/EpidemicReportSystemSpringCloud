package com.mabo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Userbase)实体类
 *
 * @author makejava
 * @since 2022-06-29 20:18:16
 */
@Data
public class Userbase implements Serializable {
    private static final long serialVersionUID = -42809674541933869L;

    private String id;

    private String name;

    private String birthday;

    private String email;

    private String phone;

    private String sex;

    private String idcardnumber;

    private String type;

    private String classId;

    private String userId;






}

