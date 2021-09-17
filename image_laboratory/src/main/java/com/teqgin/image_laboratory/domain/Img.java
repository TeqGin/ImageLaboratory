package com.teqgin.image_laboratory.domain;

import lombok.Data;

@Data
public class Img {
    private String id;

    private String name;

    private Double size;

    private String type;

    private String userId;

    private String path;

    private String dirId;

    private Integer isPublic;
}
