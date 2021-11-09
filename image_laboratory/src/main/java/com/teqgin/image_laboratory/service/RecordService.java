package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;

import java.util.List;

public interface RecordService {
    /**
     * 根据标签id获取用户上传图片的记录
     * @param labelId
     * @param userId
     * @return
     */
    Record getOneByLabel(String labelId,String userId);

    /**
     * 插入到数据库中
     * @param record
     * @return
     */
    int addOne(Record record);

    /**
     * 更新记录
     * @param record
     * @return
     */
    int updateOne(Record record);

    /**
     * 获取用户的所有上传记录
     * @param account
     * @return
     */
    List<LabelInRecordVo> getRecordsByUser(String account);

    /**
     * 更新记录或创建记录
     * @param labelId
     * @param UserId
     * @return
     */
    int updateCountOrCreate(String labelId,String UserId);
}
