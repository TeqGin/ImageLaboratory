package com.teqgin.image_laboratory.job;

import com.teqgin.image_laboratory.service.LoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserJob {
    @Autowired
    private LoginRecordService loginRecordService;


    @Async
    public void insertLoginRecordThread(String userId) {
        // 插入登陆记录
        loginRecordService.add(userId);
        log.info(Thread.currentThread().getName());
    }
}
