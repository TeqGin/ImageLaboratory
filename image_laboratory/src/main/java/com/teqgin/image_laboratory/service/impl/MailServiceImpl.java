package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.service.MailService;
import com.teqgin.image_laboratory.service.UserService;
import com.teqgin.image_laboratory.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private UserService userService;


    public boolean sendVerifyMail(String account) {
        User user = userService.getUser(account);
        if (userService.setVerifyCode(user, RandomUtil.randomNumbers(6))){
            String subject = "来自TeqGin's image station的校验码";
            String content = TextUtil.mail(user.getVerifyCode());
            MailUtil.send(account, subject, content,true);
            return true;
        }
        return false;
    }

    @Override
    public int sendMail(String account) {
        boolean success = sendVerifyMail(account);
        if (success){
            return CodeStatus.SUCCEED;
        }else {
            return CodeStatus.DATA_ERROR;
        }
    }
}
