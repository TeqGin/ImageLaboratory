package com.teqgin.image_laboratory.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Directory implements Serializable {
    private String id;

    private String name;

    private String parentId;

    private String userId;
}
