package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.dbVo.LabelInRecord;

import java.util.List;

public interface RecordService {
    Record getOneByLabel(String labelId,String userId);

    int addOne(Record record);

    List<LabelInRecord> getRecordsByUser(String account);
}
