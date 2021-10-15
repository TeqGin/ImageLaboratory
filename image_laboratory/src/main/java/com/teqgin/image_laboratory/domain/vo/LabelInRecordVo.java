package com.teqgin.image_laboratory.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LabelInRecordVo {
    private String id;

    private String labelId;

    private int count;

    private Date updateDate;

    private String userId;

    private String labelName;
}
