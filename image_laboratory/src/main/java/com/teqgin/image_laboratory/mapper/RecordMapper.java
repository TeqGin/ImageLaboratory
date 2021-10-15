package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    @Select("select r.*,l.name as label_name\n" +
            "from record r\n" +
            "left join label l\n" +
            "on r.label_id = l.id\n" +
            "where r.user_id = #{account}")
    List<LabelInRecordVo> findRecordsByAccount(String id);
}
