package com.teqgin.image_laboratory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRecord implements Serializable {
    private String id;

    private String loginWayId;

    private String deviceId;

    private String systemId;

    private Date loginDate;

    private String userId;
}
