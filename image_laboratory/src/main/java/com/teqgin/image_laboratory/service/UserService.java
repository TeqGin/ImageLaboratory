package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface UserService {
    /**
     * 通过账号获取用户对象
     * @param account
     * @return
     */
    User getUser(String account);

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    int changePassword(String password,String id);

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
     * 修改密码
     * @param user
     * @return
     */
    int modifyInfo(User user);

    /**
     * 注销账号
     * @param request
     * @param verifyCode
     * @return
     */
    int killAccount(HttpServletRequest request, String verifyCode);

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



    void saveToDatabase(User user,byte[] source);

    /**
     * 用户上传文件
     * @param doc
     */
    boolean upload(MultipartFile doc, HttpServletRequest request) throws IOException;

    /**
     * 下载本地图片
     * @param response
     * @param id
     * @throws IOException
     */
    void download( HttpServletResponse response,String id) throws IOException;

    /**
     * 申诉
     * @param request
     * @param account
     * @param verifyCode
     * @return
     */
    String appeal(HttpServletRequest request, String account, String verifyCode);

    /**
     * 添加到云空间
     * @param request
     * @param url
     * @throws IOException
     */
    void addToCloud(HttpServletRequest request, String url) throws IOException;
}
