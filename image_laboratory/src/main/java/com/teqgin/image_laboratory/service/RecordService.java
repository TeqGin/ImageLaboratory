package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;

import java.util.List;

public interface RecordService {
    Record getOneByLabel(String labelId,String userId);

    int addOne(Record record);

    int updateOne(Record record);

    List<LabelInRecordVo> getRecordsByUser(String account);

    int updateCountOrCreate(String labelId,String UserId);
}
