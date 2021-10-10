package com.teqgin.image_laboratory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.dbVo.LabelInRecord;
import com.teqgin.image_laboratory.mapper.RecordMapper;
import com.teqgin.image_laboratory.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Record getOneByLabel(String labelId, String userId) {
        var condition = new QueryWrapper<Record>();
        condition.eq("label_id", labelId);
        condition.eq("user_id", userId);
        return recordMapper.selectOne(condition);
    }

    @Override
    public int addOne(Record record) {
        return recordMapper.insert(record);
    }

    @Override
    public List<LabelInRecord> getRecordsByUser(String account) {
        return recordMapper.findRecordsByAccount(account);
    }
}
