package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.Label;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LabelMapper extends BaseMapper<Label> {
}
