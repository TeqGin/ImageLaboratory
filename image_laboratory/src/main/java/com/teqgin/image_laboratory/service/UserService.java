package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User getUser(String account);

    boolean setVerifyCode(User user, String code);

    boolean isUserLegal(User user);

    boolean isUserExists(String account);

    boolean addUser(User user);

    int changePassword(User user);

    User getCurrentUser(HttpServletRequest request);

    void setCurrentUser(HttpServletRequest request, User user);

    void initDirectory(HttpServletRequest request);

    void deployAllInfo(HttpServletRequest request);
}
