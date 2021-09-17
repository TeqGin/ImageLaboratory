package com.teqgin.image_laboratory;

import com.teqgin.image_laboratory.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTest {
    @Autowired
    public MailService mailService;
    @Test
    public void sendMail(){
        mailService.sendMail("1102647596@qq.com");
    }
}
