package com.teqgin.image_laboratory.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("用户类")
public class User implements Serializable {
    @ApiModelProperty("主键")
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
