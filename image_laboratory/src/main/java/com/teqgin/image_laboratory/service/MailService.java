package com.teqgin.image_laboratory.service;

public interface MailService {
    /**
     * 废弃不用
     * @param account
     */
    void start(String account);

    /**
     * 通过账号（即邮箱）发送验证码
     * @param account
     * @return
     */
    int sendMail(String account);
}
