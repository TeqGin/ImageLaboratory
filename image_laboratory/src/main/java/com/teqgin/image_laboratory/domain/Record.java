package com.teqgin.image_laboratory.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Record {
    private String id;

    private String labelId;

    private int count;

    private Date updateDate;

    private String userId;
}
