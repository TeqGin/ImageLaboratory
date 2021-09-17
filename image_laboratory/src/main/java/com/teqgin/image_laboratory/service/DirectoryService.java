package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DirectoryService {

    String getCurrentPath(HttpServletRequest request);

    void setCurrentPath(HttpServletRequest request, String path);

    List<Directory> getChildDirectory(String parentId);

    Directory createDirectory(HttpServletRequest request,String name) throws FileCreateFailureException;


    String backLastDirectory(HttpServletRequest request);

    void pathForward(String name,HttpServletRequest request);

    String pathGoBack(String path);

    void enterNextDirectory(String id,HttpServletRequest request);

    Directory getCurrentDirectory(HttpServletRequest request);

    void setCurrentDirectory(HttpServletRequest request, Directory directory);

    Directory getRoot(String name);

}
