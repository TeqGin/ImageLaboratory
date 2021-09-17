package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.Img;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ImgMapper extends BaseMapper<Img> {
}
