package com.teqgin.image_laboratory.service;

public interface MailService {
    void start(String account);

    int sendMail(String account);
}
