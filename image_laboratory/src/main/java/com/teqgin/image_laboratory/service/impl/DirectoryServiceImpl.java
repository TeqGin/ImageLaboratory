package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.mapper.DirectoryMapper;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 处理关于文件夹的创建、删除、以及文件夹层级结构的实现
 *
 */

@Service
@Slf4j
public class DirectoryServiceImpl implements DirectoryService {
    @Autowired
    private DirectoryMapper directoryMapper;
    @Autowired
    private UserService userService;

    /**
     * 获得用户当前的文件夹路径
     * @param request
     * @return
     */
    @Override
    public String getCurrentPath(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("currentPath");
    }

    /**
     * 设置用户当前的文件夹路径
     * @param request
     * @param path
     */
    @Override
    public void setCurrentPath(HttpServletRequest request, String path) {
        request.getSession().setAttribute("currentPath", path);
    }

    /**
     * 根据父级文件夹id获取子文件夹
     * @param parentId
     * @return
     */
    @Override
    public List<Directory> getChildDirectory(String parentId) {
        var condition = new QueryWrapper<Directory>();
        condition.eq("parent_id", parentId);
        return directoryMapper.selectList(condition);
    }

    /**
     * 创建文件夹
     * @param request
     * @param name
     * @return
     * @throws FileCreateFailureException
     */
    @Override
    public Directory createDirectory(HttpServletRequest request,String name) throws FileCreateFailureException {
        String newDirectoryPath = getCurrentPath(request) + name + "/";
        Directory directory = getCurrentDirectory(request);

        User user = userService.getCurrentUser(request);

        //创建文件夹
        File file = new File(newDirectoryPath);
        if (!file.exists()){
            boolean succeed = file.mkdirs();
            log.info("创建文件夹"+ name  + (succeed ?"成功!":"失败!"));
            if (!succeed){
                throw new FileCreateFailureException();
            }
        }

        //把创建的文件夹对象数据插入数据库中
        Directory dir = new Directory();
        dir.setId(IdUtil.getSnowflake().nextIdStr());
        dir.setName(name);
        dir.setParentId(directory == null ? null: directory.getId());
        dir.setUserId(user.getId());

        int i = directoryMapper.insert(dir);
        return dir;
    }
    /**
     * 返回上一级文件夹
     * @param request
     * @return 上一级文件夹的id
     */
    @Override
    public String backLastDirectory(HttpServletRequest request) {
        String currentPath = getCurrentPath(request);
        Directory directory = getCurrentDirectory(request);

        //将路径回退到上级路径
        currentPath = pathGoBack(currentPath);
        setCurrentPath(request, currentPath);

        directory = directoryMapper.selectById(directory.getParentId());
        setCurrentDirectory(request, directory);
        return directory.getId();
    }


    /**
     * 将路径设置成下一级路径
     * @param name
     * @param request
     */
    public void pathForward(String name,HttpServletRequest request) {
        String beforePath = getCurrentPath(request);
        setCurrentPath(request, beforePath + name + "/");
    }


    /**
     * 将形如 root/cc/mm/zz/ 回退到 root/cc/mm
     * @param path
     * @return
     */
    public String pathGoBack(String path) {
        if (path.split("/").length <= 1){
            return path;
        }else {
            int end = path.length() - getCurrentName(path).length() - 1;
            return path.substring(0,end);
        }
    }
    /**
     * 进入下一级文件夹，并设置session中的值为下一级路径
     * @param id
     * @param request
     */
    @Override
    public void enterNextDirectory(String id,HttpServletRequest request) {
        Directory directory = directoryMapper.selectById(id);
        setCurrentDirectory(request,directory);
        pathForward(directory.getName(),request);
    }
    /**
     * 获取当前文件夹实体
     * @param request
     * @return
     */
    @Override
    public Directory getCurrentDirectory(HttpServletRequest request) {
        return (Directory)request.getSession().getAttribute("directory");
    }
    /**
     * 设置当前文件夹实体
     * @param request
     * @param directory
     */
    @Override
    public void setCurrentDirectory(HttpServletRequest request, Directory directory) {
        request.getSession().setAttribute("directory", directory);
    }
    /**
     * 获取根文件夹实体
     * @param name
     * @return
     */
    @Override
    public Directory getRootDirectory(String name) {
        return directoryMapper.findRoot(name);
    }

    @Override
    public boolean isAtRoot(HttpServletRequest request) {
        String currentName =  getCurrentName(getCurrentPath(request));
        String userName =  userService.getCurrentUser(request).getAccount();
        return currentName.equals(userName);
    }

    @Override
    public void delete(String id,HttpServletRequest request) {
        Directory directory = directoryMapper.selectById(id);
        String path = getCurrentPath(request) + "/" + directory.getName();
        File file = new File(path);
        FileUtil.del(file);
        directoryMapper.deleteById(directory.getId());
    }

    @Override
    public void rename(HttpServletRequest request, String name, String directoryId) {
        Directory old = directoryMapper.selectById(directoryId);
        String oldPath = getCurrentPath(request) + "/" + old.getName();
        File oldFile = new File(oldPath);
        FileUtil.rename(oldFile,name,true);
        old.setName(name);
        directoryMapper.updateById(old);
    }

    /**
     * 获取当前文件夹的文件名
     * @param currentPath
     * @return
     */
    public String getCurrentName(String currentPath){
        if (currentPath.equals("")){
            return "";
        }else {
            String [] split = currentPath.split("/");
            return split[split.length -1];
        }
    }
}
