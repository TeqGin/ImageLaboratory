package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DirectoryService {

    /**
     * 获得用户当前的文件夹路径
     * @param request
     * @return
     */
    String getCurrentPath(HttpServletRequest request);

    /**
     * 设置用户当前的文件夹路径
     * @param request
     * @param path
     */
    void setCurrentPath(HttpServletRequest request, String path);

    /**
     * 根据父级文件夹id获取子文件夹
     * @param parentId
     * @return
     */
    List<Directory> getChildDirectory(String parentId);

    /**
     * 创建文件夹
     * @param request
     * @param name
     * @return
     * @throws FileCreateFailureException
     */
    Directory createDirectory(HttpServletRequest request,String name) throws FileCreateFailureException;


    /**
     * 返回上一级文件夹
     * @param request
     * @return 上一级文件夹的id
     */
    String backLastDirectory(HttpServletRequest request);


    /**
     * 进入下一级文件夹，并设置session中的值为下一级路径
     * @param id
     * @param request
     */
    void enterNextDirectory(String id,HttpServletRequest request);

    /**
     * 获取当前文件夹实体
     * @param request
     * @return
     */
    Directory getCurrentDirectory(HttpServletRequest request);

    /**
     * 设置当前文件夹实体
     * @param request
     * @param directory
     */
    void setCurrentDirectory(HttpServletRequest request, Directory directory);

    /**
     * 获取根文件夹实体
     * @param name
     * @return
     */
    Directory getRootDirectory(String name);

    /**
     * 判断当前路径是否已经在根路径
     * @param request
     * @return
     */
    boolean isAtRoot(HttpServletRequest request);

    void delete(String id,HttpServletRequest request);

    void rename(HttpServletRequest request, String name, String directoryId);
}
