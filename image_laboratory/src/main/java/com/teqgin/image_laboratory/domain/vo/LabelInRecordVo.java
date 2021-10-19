package com.teqgin.image_laboratory.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LabelInRecordVo implements Serializable {
    private String id;

    private String labelId;

    private int count;

    private Date updateDate;

    private String userId;

    private String labelName;
}
