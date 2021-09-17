package com.teqgin.image_laboratory.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;

    private String name;

    private String password;

    private String account;

    private String verifyCode;

    private String smsCode;

    private String phone;

    private Integer roleId;

    private Date verifyCodeTime;

    private Date smsCodeTime;
}
