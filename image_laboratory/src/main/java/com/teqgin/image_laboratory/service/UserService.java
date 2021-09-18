package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * 通过账号获取用户对象
     * @param account
     * @return
     */
    User getUser(String account);

    /**
     * 设置验证码
     * @param user
     * @param code
     * @return
     */
    boolean setVerifyCode(User user, String code);

    /**
     * 判断用户是否合法
     * @param user
     * @return
     */
    boolean isUserLegal(User user);

    /**
     * 判断用户是否存在
     * @param account
     * @return
     */
    boolean isUserExists(String account);

    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int changePassword(User user);

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * 设置当前用户
     * @param request
     * @param user
     */
    void setCurrentUser(HttpServletRequest request, User user);

    /**
     * 用户登陆时初始化 当前路径、当前文件夹路径信息
     * @param request
     */
    void initDirectory(HttpServletRequest request);

    /**
     * 清除session信息
     * @param request
     */
    void deployAllInfo(HttpServletRequest request);
}
