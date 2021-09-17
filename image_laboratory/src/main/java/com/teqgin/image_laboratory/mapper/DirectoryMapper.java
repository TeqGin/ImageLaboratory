package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.teqgin.image_laboratory.domain.Directory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DirectoryMapper extends BaseMapper<Directory> {

    @Select("select * from directory where name = #{name} and parent_id is null")
    Directory findRoot(String name);
}
