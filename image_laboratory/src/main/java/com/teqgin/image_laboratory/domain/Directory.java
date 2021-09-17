package com.teqgin.image_laboratory.domain;

import lombok.Data;

@Data
public class Directory {
    private String id;

    private String name;

    private String parentId;

    private String userId;
}
