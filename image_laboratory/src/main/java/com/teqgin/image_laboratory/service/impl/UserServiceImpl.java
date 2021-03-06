package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.baidu.aip.util.Base64Util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.domain.*;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.job.UserJob;
import com.teqgin.image_laboratory.mapper.UserMapper;
import com.teqgin.image_laboratory.service.*;
import com.teqgin.image_laboratory.util.FileTypeUtil;
import com.teqgin.image_laboratory.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ImgService imgService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private HttpService httpService;

    @Autowired
    private UserJob userJob;

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

    @Override
    public int changePassword(String password, String id) {
        return userMapper.updatePassword(password,id);
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
        user.setVerifyCodeTime(new Date());
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
        // 获取真实用户信息
        User real = getUser(user.getAccount());
        // 判断验证码是否正确并且未过期
        if (real.getVerifyCode().equals(user.getVerifyCode())
                && DateUtil.between(real.getVerifyCodeTime(),new Date(), DateUnit.MINUTE) <= 10){
            real.setPassword(SecureUtil.md5(user.getPassword()));
            userMapper.updatePassword(real.getPassword(),real.getId());
            return CodeStatus.SUCCEED;
        }
        return CodeStatus.DATA_ERROR;
    }

    @Override
    public int modifyInfo(User user) {
        userMapper.updateById(user);
        return CodeStatus.SUCCEED;
    }

    @Override
    public int modifyPhone(User user) {
        userMapper.updatePhone(user.getId(), user.getPhone());
        return CodeStatus.SUCCEED;
    }

    @Override
    public int modifyNickName(User user) {
        userMapper.updateNickName(user.getId(), user.getName());
        return CodeStatus.SUCCEED;
    }

    @Override
    @Transactional
    public int killAccount(HttpServletRequest request, String verifyCode) {
        User user = getCurrentUser(request);
        String userId = user.getId();
        String directoryId = directoryService.getRootDirectory(user.getAccount()).getId();

        int row = 0;
        if (isCodeLegal(userId, verifyCode) ){
            deleteRelativeFile(userId, directoryService.getFullPath(directoryId));
            row = userMapper.deleteById(userId);
        }
        return row;
    }

    private void deleteRelativeFile(String userId, String directoryPath) {
        loginRecordService.deleteByUserId(userId);
        imgService.deleteByUserId(userId);
        directoryService.deleteByUserId(userId);
        recordService.deleteByUserId(userId);

        File files = new File(directoryPath);
        FileUtil.del(files);
    }

    private boolean isCodeLegal(String userId, String verifyCode){
        User real = userMapper.selectById(userId);
        return real.getVerifyCode().equals(verifyCode)
                && DateUtil.between(real.getVerifyCodeTime(),new Date(), DateUnit.MINUTE) <= 10;
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
                e.printStackTrace();
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
    @Transactional
    public boolean upload(MultipartFile doc,HttpServletRequest request) throws IOException {
        File image = new File(directoryService.getCurrentPath(request) + "/" + doc.getOriginalFilename());
        //获得后缀名
        String suffixName = doc.getOriginalFilename().substring(doc.getOriginalFilename().lastIndexOf("."));
        String type = FileTypeUtil.getFileType(suffixName);
        if(!canInsertImage(image.getAbsolutePath())){
            return false;
        }
        byte[] docBytes = doc.getBytes();
        FileUtil.mkParentDirs(image);
        if (!image.exists()){
            image.createNewFile();
        }
        doc.transferTo(image.getAbsoluteFile());


        User user = getCurrentUser(request);
        Directory currentDirectory = directoryService.getCurrentDirectory(request);
        // 存入数据库
        saveImage(image,currentDirectory,user, type);
        // 异步执行，防止前端上传图片时间过长
        if(type.equals("image")){
            userJob.saveToDatabase(user,docBytes);
        }
        return true;
    }

    private boolean canInsertImage(String path){
         return !imgService.isImgExist(path);
    }

    private String saveImage(File image,Directory currentDirectory, User user, String type){
        // 插入image表
        Img img = new Img();
        img.setDirId(currentDirectory.getId());
        img.setId(IdUtil.getSnowflake().nextIdStr());
        img.setIsPublic(0);
        img.setName(image.getName());
        img.setPath(image.getAbsolutePath());
        img.setUserId(user.getId());
        img.setInsertDate(new Date());
        img.setType(type);

        imgService.save(img);
        return img.getId();
    }

    @Override
    public void download(HttpServletResponse response, String id) throws IOException {
        Img img = imgService.getById(id);
        File target;
        if (img != null){
            target = new File(directoryService.getFullPath(img.getDirId())+ "/" + img.getName());
        }else {
            img = imgService.getPublicImage(id);
            target = new File(img.getPath());
        }

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

    @Override
    public String appeal(HttpServletRequest request, String account, String verifyCode) {
        String userId = getCurrentUser(request).getId();
        if (isCodeLegal(userId, verifyCode)){
            String password = UUID.randomUUID().toString().replace("-","").substring(8);
            userMapper.updatePasswordByAccount(account, SecureUtil.md5(password));
            return password;
        }
        return "";
    }

    @Override
    public void addToCloud(HttpServletRequest request, String url) throws IOException {
        log.info("待下载的url:" + url);
        String filename = UUID.randomUUID().toString().substring(0,6) + ".jpg";
        String path = createImgPath(request,filename);

        File target = FileUtil.file(path);
        FileUtil.mkParentDirs(target);
        if (!target.exists()){
            target.createNewFile();
        }
        FileUtils.downloadUrl(url, target);
        saveUrlImage2Database(request,path,filename);
    }

    @Override
    public void saveTransferredImage(HttpServletRequest request, String base64) throws IOException {
        String filename = UUID.randomUUID().toString().substring(0,6) + ".jpg";
        String path = createImgPath(request,filename);
        File target = FileUtil.file(path);
        FileUtils.base642Image(base64, target);

        saveUrlImage2Database(request,path,filename);
    }


    private int saveUrlImage2Database(HttpServletRequest request, String path,String filename){
        User user = getCurrentUser(request);
        Directory directory = directoryService.getRootDirectory(user.getAccount());
        // 将图片插入img表中
        Img img = new Img();
        img.setId(IdUtil.getSnowflake().nextIdStr());
        img.setDirId(directory.getId());
        img.setUserId(user.getId());
        img.setPath(path);
        img.setInsertDate(new Date());
        img.setName(filename);
        img.setIsPublic(0);
        img.setType("image");

        return imgService.save(img);
    }

    private String createImgPath(HttpServletRequest request, String fileName){
        StringBuilder path = new StringBuilder();
        path.append(prefix);
        path.append("/");
        path.append(getCurrentUser(request).getAccount());
        path.append("/");
        path.append(fileName);
        return path.toString();
    }



    @Override
    public void saveToDatabase(User user,byte[] source) {
        String labelName = httpService.getTag(Base64Util.encode(source));
        // 插入label表
        Label label = labelService.getOneByName(labelName);
        if (label == null && labelName != null){
            label = new Label();
            label.setId(IdUtil.getSnowflake().nextIdStr());
            label.setName(labelName);

            labelService.addOne(label);
        }

        // 插入record记录
        if (label != null){
            recordService.updateCountOrCreate(label.getId(),user.getId());
        }
    }


}
