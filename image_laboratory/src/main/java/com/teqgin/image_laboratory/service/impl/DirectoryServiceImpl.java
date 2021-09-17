package com.teqgin.image_laboratory.service.impl;

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

    @Override
    public String getCurrentPath(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("currentPath");
    }

    @Override
    public void setCurrentPath(HttpServletRequest request, String path) {
        request.getSession().setAttribute("currentPath", path);
    }

    @Override
    public List<Directory> getChildDirectory(String parentId) {
        var condition = new QueryWrapper<Directory>();
        condition.eq("parent_id", parentId);
        return directoryMapper.selectList(condition);
    }

    @Override
    public Directory createDirectory(HttpServletRequest request,String name) throws FileCreateFailureException {
        String newDirectoryPath = getCurrentPath(request) + name + "//";
        Directory directory = getCurrentDirectory(request);

        User user = userService.getCurrentUser(request);

        File file = new File(newDirectoryPath);
        if (!file.exists()){
            boolean succeed = file.mkdirs();
            log.info("创建文件夹"+ name  + (succeed ?"成功!":"失败!"));
            if (!succeed){
                throw new FileCreateFailureException();
            }
        }

        Directory dir = new Directory();
        dir.setId(IdUtil.getSnowflake().nextIdStr());
        dir.setName(name);
        dir.setParentId(directory == null ? null: directory.getId());
        dir.setUserId(user.getId());

        int i = directoryMapper.insert(dir);
        return dir;
    }

    @Override
    public String backLastDirectory(HttpServletRequest request) {
        String currentPath = getCurrentPath(request);
        Directory directory = getCurrentDirectory(request);
        currentPath = pathGoBack(currentPath);
        setCurrentPath(request, currentPath);
        directory = directoryMapper.selectById(directory.getParentId());
        setCurrentDirectory(request, directory);
        return directory.getId();
    }

    @Override
    public void pathForward(String name,HttpServletRequest request) {
        String beforePath = getCurrentPath(request);
        setCurrentPath(request, beforePath + name + "//");
    }

    @Override
    //"root/cc/mm/zz/
    public String pathGoBack(String path) {
        if (path.split("/").length <= 1){
            return path;
        }else {
            int end = path.length() - getCurrentName(path).length() - 1;
            return path.substring(0,end);
        }
    }

    @Override
    public void enterNextDirectory(String id,HttpServletRequest request) {
        Directory directory = directoryMapper.selectById(id);
        pathForward(directory.getName(),request);
    }

    @Override
    public Directory getCurrentDirectory(HttpServletRequest request) {
        return (Directory)request.getSession().getAttribute("directory");
    }

    @Override
    public void setCurrentDirectory(HttpServletRequest request, Directory directory) {
        request.getSession().setAttribute("directory", directory);
    }

    @Override
    public Directory getRoot(String name) {
        return directoryMapper.findRoot(name);
    }

    public String getCurrentName(String currentPath){
        if (currentPath.equals("")){
            return "";
        }else {
            String [] split = currentPath.split("/");
            return split[split.length -1];
        }
    }
}
