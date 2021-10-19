package com.teqgin.image_laboratory.service;

public interface MailService {

    /**
     * 通过账号（即邮箱）发送验证码
     * @param account
     * @return
     */
    int sendMail(String account);
}
