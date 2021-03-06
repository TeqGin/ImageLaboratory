package com.teqgin.image_laboratory.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Img implements Serializable {
    private String id;

    private String name;

    private Double size;

    private String type;

    private String userId;

    private String path;

    private String dirId;

    private Integer isPublic;

    private Date insertDate;
}
