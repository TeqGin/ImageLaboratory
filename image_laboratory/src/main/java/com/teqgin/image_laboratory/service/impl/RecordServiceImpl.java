package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import com.teqgin.image_laboratory.mapper.RecordMapper;
import com.teqgin.image_laboratory.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public int updateOne(Record record) {
        return recordMapper.updateById(record);
    }

    @Override
    public List<LabelInRecordVo> getRecordsByUser(String id) {
        return recordMapper.findRecordsByAccount(id);
    }

    @Override
    public int updateCountOrCreate(String labelId, String userId) {
        // 插入record表
        Record record = getOneByLabel(labelId, userId);
        if (record == null){
            record = new Record();
            record.setId(IdUtil.getSnowflake().nextIdStr());
            record.setCount(1);
            record.setLabelId(labelId);
            record.setUpdateDate(new Date());
            record.setUserId(userId);
            // 不存在则插入
            return addOne(record);
        }else {
            record.setCount(record.getCount() + 1);
            record.setUpdateDate(new Date());
            // 存在则更新
            return updateOne(record);
        }
    }
}
