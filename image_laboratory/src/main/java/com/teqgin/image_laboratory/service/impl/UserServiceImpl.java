package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.Helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.mapper.UserMapper;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ImgService imgService;

    @Value("${upload.path}")
    private String prefix;

    /**
     * 通过账号获取用户对象
     * @param account
     * @return
     */
    @Override
    public User getUser(String account) {
        var condition = new QueryWrapper<User>();
        condition.eq("account",account);
        return  userMapper.selectOne(condition);
    }
    /**
     * 设置验证码
     * @param user
     * @param code
     * @return
     */
    @Override
    public boolean setVerifyCode(User user, String code) {
        user.setVerifyCode(code);
        userMapper.updateById(user);
        return true;
    }
    /**
     * 判断用户是否合法
     * @param user
     * @return
     */
    @Override
    public boolean isUserLegal(User user) {
        return userMapper.verifyAccount(user.getAccount(), SecureUtil.md5(user.getPassword())) != null;
    }
    /**
     * 判断用户是否存在
     * @param account
     * @return
     */
    @Override
    public boolean isUserExists(String account) {
        return userMapper.selectById(account) != null;
    }

    @Override
    public boolean addUser(User user) {
        userMapper.insert(user);
        return true;
    }
    /**
     * 修改密码
     * @param user
     * @return
     */
    @Override
    public int changePassword(User user) {
        //获取真实用户信息
        User real = getUser(user.getAccount());
        //判断验证码是否正确
        if (real.getVerifyCode().equals(user.getVerifyCode())){
            real.setPassword(SecureUtil.md5(user.getPassword()));
            userMapper.updateById(real);
            return CodeStatus.SUCCEED;
        }
        return CodeStatus.DATA_ERROR;
    }
    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return (User)request.getSession().getAttribute("user");
    }
    /**
     * 设置当前用户
     * @param request
     * @param user
     */
    @Override
    public void setCurrentUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }
    /**
     * 用户登陆时初始化 当前路径、当前文件夹路径信息
     * @param request
     */
    @Override
    public void initDirectory(HttpServletRequest request) {
        User user = getCurrentUser(request);
        String path = prefix;
        directoryService.setCurrentPath(request, path);
        Directory directory = directoryService.getRootDirectory(user.getAccount());
        if (directory == null){
            try {
                directory = directoryService.createDirectory(request, user.getAccount());
            } catch (FileCreateFailureException e) {

            }
        }

        //设置用户登陆后的相关信息
        directoryService.setCurrentDirectory(request, directory);
        path += user.getAccount() + "/";
        directoryService.setCurrentPath(request, path);
    }
    /**
     * 清除session信息
     * @param request
     */
    @Override
    public void deployAllInfo(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("currentPath");
        request.getSession().removeAttribute("directory");
        request.getSession().invalidate();
    }

    @Override
    public void upload(MultipartFile doc,HttpServletRequest request) throws IOException {
        File image = new File(directoryService.getCurrentPath(request) + "/" + doc.getOriginalFilename());
        FileUtil.mkParentDirs(image);
        if (!image.exists()){
            image.createNewFile();
        }
        doc.transferTo(image.getAbsoluteFile());
        saveToDatabase(image,request);
    }

    @Override
    public void download(HttpServletResponse response, String id) throws IOException {
        Img img = imgService.getById(id);
        File target = new File(img.getPath());
        if (target.exists()){
            try(FileInputStream fis = new FileInputStream(target);
                BufferedInputStream bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream()) {

                String fileName = img.getName();
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                //set response head
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

                byte []buffer =new byte[1024];
                //write file
                int i = bis.read(buffer);
                while (i != -1){
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
        }
    }

    private void saveToDatabase(File image,HttpServletRequest request){
        Img img = new Img();
        img.setDirId(directoryService.getCurrentDirectory(request).getId());
        img.setId(IdUtil.getSnowflake().nextIdStr());
        img.setIsPublic(0);
        img.setName(image.getName());
        img.setPath(image.getAbsolutePath());
        img.setUserId(getCurrentUser(request).getId());

        imgService.save(img);
    }

}
