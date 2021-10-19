package com.teqgin.image_laboratory.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Label implements Serializable {
    private String id;

    private String name;
}
