package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.LoginRecord;
import com.teqgin.image_laboratory.domain.vo.LoginRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {
    List<LoginRecordVo> findLoginRecordLimit(String userId);

    List<LoginRecordVo> findAll(String userId);
}
