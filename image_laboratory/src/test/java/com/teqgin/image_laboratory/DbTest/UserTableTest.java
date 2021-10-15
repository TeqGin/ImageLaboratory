package com.teqgin.image_laboratory.DbTest;

import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTableTest {
    @Autowired
    public UserMapper userMapper;

    @Test
    public void isConnected(){
        var users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void MybatisXmlTest(){
        userMapper.findAll().forEach(System.out::println);
    }
}
