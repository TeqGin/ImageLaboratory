package com.teqgin.image_laboratory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teqgin.image_laboratory.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface  UserMapper extends BaseMapper<User> {
    @Select("Select id from user where account = #{account} and password = #{password}")
    String verifyAccount(String account, String password);

    List<User> findAll();

    int updatePassword(String password, String id);

    int updatePasswordByAccount(String account, String password);

}
