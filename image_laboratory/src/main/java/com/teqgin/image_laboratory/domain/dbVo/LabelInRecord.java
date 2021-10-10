package com.teqgin.image_laboratory.domain.dbVo;

import lombok.Data;

import java.util.Date;

@Data
public class LabelInRecord {
    private String id;

    private String labelId;

    private int count;

    private Date updateDate;

    private String userId;

    private String labelName;
}
