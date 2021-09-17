package com.teqgin.image_laboratory.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.Helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.mapper.UserMapper;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DirectoryService directoryService;

    @Value("${upload.path}")
    private String prefix;

    @Override
    public User getUser(String account) {
        var condition = new QueryWrapper<User>();
        condition.eq("account",account);
        return  userMapper.selectOne(condition);
    }

    @Override
    public boolean setVerifyCode(User user, String code) {
        user.setVerifyCode(code);
        userMapper.updateById(user);
        return true;
    }

    @Override
    public boolean isUserLegal(User user) {
        return userMapper.verifyAccount(user.getAccount(), SecureUtil.md5(user.getPassword())) != null;
    }

    @Override
    public boolean isUserExists(String account) {
        return userMapper.selectById(account) != null;
    }

    @Override
    public boolean addUser(User user) {
        userMapper.insert(user);
        return true;
    }

    @Override
    public int changePassword(User user) {
        User real = getUser(user.getAccount());
        if (real.getVerifyCode().equals(user.getVerifyCode())){
            real.setPassword(SecureUtil.md5(user.getPassword()));
            userMapper.updateById(real);
            return CodeStatus.SUCCEED;
        }
        return CodeStatus.DATA_ERROR;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return (User)request.getSession().getAttribute("user");
    }

    @Override
    public void setCurrentUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    @Override
    public void initDirectory(HttpServletRequest request) {
        User user = getCurrentUser(request);
        String path = prefix;
        directoryService.setCurrentPath(request, path);
        Directory directory = directoryService.getRoot(user.getAccount());
        if (directory == null){
            try {
                directory = directoryService.createDirectory(request, user.getAccount());
            } catch (FileCreateFailureException e) {

            }
        }

        directoryService.setCurrentDirectory(request, directory);
        path += user.getAccount() + "//";
        directoryService.setCurrentPath(request, path);
    }

    @Override
    public void deployAllInfo(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("currentPath");
        request.getSession().removeAttribute("directory");
        request.getSession().invalidate();
    }

}
