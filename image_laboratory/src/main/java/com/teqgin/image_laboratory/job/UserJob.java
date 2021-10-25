package com.teqgin.image_laboratory.job;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.service.LoginRecordService;
import com.teqgin.image_laboratory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class UserJob {
    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private UserService userService;

    @Async
    public void insertLoginRecordThread(String userId) {
        // 插入登陆记录
        loginRecordService.add(userId);
    }

    @Async
    public void saveToDatabase(User user,byte[] source) {
        userService.saveToDatabase(user,source);
    }
}
