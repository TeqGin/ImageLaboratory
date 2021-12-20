package com.teqgin.image_laboratory.controller;


import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.helper.CodeStatus;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件夹相关的操作
 * @date 2021.09.18
 * @author xdf
 */
@Controller
@RequestMapping("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private ImgService imgService;

    @Autowired
    private UserService userService;

    /**
     * 进入下一个文件夹
     * @param request
     * @param directoryId
     * @return
     */
    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<?> next(HttpServletRequest request, @RequestParam String directoryId){
        var directories = directoryService.getChildDirectory(directoryId);
        directoryService.enterNextDirectory(directoryId, request);

        var body = new HashMap<String, Object>();
        body.put("directories", directories);
        body.put("code", CodeStatus.SUCCEED);
        body.put("message", "查找文件夹成功");
        return ResponseEntity.ok(body);
    }

    /**
     * 创建文件夹
     * @param request
     * @param name
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createDirectory(HttpServletRequest request, @RequestParam String name){
        var body = new HashMap<String, Object>();
        try{
            Directory directory = directoryService.createDirectory(request, name);
            body.put("directory", directory);
            body.put("code", CodeStatus.SUCCEED);
        }catch (FileCreateFailureException e){
            body.put("code", CodeStatus.DATA_ERROR);
            body.put("message", e.getMessage());
        }
        return ResponseEntity.ok(body);
    }

    /**
     * 危险操作
     * 根据id删除文件夹及其子文件
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam String id,@RequestParam int isDirectory,HttpServletRequest request){
        var body = new HashMap<String, Object>();
        if (isDirectory == 1){
            boolean success = directoryService.delete(id,request);
            if (!success){
                body.put("code",CodeStatus.NO_CHANGE);
                body.put("message", "删除失败,该文件夹下还有其他文件");
            }else {
                body.put("code",CodeStatus.SUCCEED);
            }
        }else if (isDirectory == 2){
            imgService.delete(id,request);
            body.put("code",CodeStatus.SUCCEED);
        }else {
            body.put("code",CodeStatus.DATA_ERROR);
            return ResponseEntity.ok(body);
        }
        return ResponseEntity.ok(body);
    }

    /**
     * 返回上级或根目录，根据root的值进行判断，
     * @param request
     *  root 0:返回上级目录，1:返回根目录
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public ResponseEntity<?> backDirectory(HttpServletRequest request){
        // 如果已经在根路径了，则提示已在根路径
        if (directoryService.isAtRoot(request)){
            var body = new HashMap<String, Object>();
            body.put("code",CodeStatus.NO_CHANGE);
            return ResponseEntity.ok(body);
        }
        // 实现返回上一级路径
        String id = directoryService.backLastDirectory(request);
        var directories = directoryService.getChildDirectory(id);
        var body = new HashMap<String, Object>();
        body.put("directories", directories);
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    /**
     * 重命名文件或文件夹
     * @param name
     * @param id
     * @param isDirectory
     * @param request
     * @return
     */
    @PostMapping("/rename")
    @ResponseBody
    public ResponseEntity<?> rename(@RequestParam("name")String name,
                                    @RequestParam("id")String id,
                                    @RequestParam int isDirectory,
                                    HttpServletRequest request) throws IOException {
        var body = new HashMap<String, Object>();
        if (isDirectory == 1){
            directoryService.rename(request,name,id);
        }else if (isDirectory == 2){
            imgService.rename(request,name,id);
        }else {
            body.put("code",CodeStatus.DATA_ERROR);
            return ResponseEntity.ok(body);
        }

        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    /**
     * 移动文件或文件夹
     * @param srcId
     * @param targetId
     * @param isDirectory
     * @param request
     * @return
     */
    @PostMapping("/move")
    @ResponseBody
    public ResponseEntity<?> move(@RequestParam("src_id")String srcId,
                                  @RequestParam("target_id")String targetId,
                                  @RequestParam int isDirectory,
                                  HttpServletRequest request){
        var body = new HashMap<String, Object>();
        if (isDirectory == 1){
            directoryService.move(srcId, targetId, request);
        }else if (isDirectory == 2){
            imgService.move(srcId, targetId, request);
        }else {
            body.put("code",CodeStatus.DATA_ERROR);
            return ResponseEntity.ok(body);
        }
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }


    @PostMapping("/sort")
    @ResponseBody
    public ResponseEntity<?> sort(HttpServletRequest request, @RequestParam("way") int way){
        var body = new HashMap<String, Object>();
        String parentId = directoryService.getCurrentDirectory(request).getId();
        List<Directory> directoryList = directoryService.getChildDirectorySorted(parentId, way);
        List<Img> imgList = imgService.getImagesByParentIdSorted(parentId,way);

        body.put("directories", directoryList);
        body.put("images",imgList);
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<?> search(HttpServletRequest request,@RequestParam("keyword")String keyword){
        var body = new HashMap<String, Object>();
        String parentId = directoryService.getCurrentDirectory(request).getId();
        List<Directory> directoryList = directoryService.SearchChildDirectory(parentId, keyword);
        List<Img> imgList = imgService.SearchImagesByParentId(parentId,keyword);

        body.put("directories", directoryList);
        body.put("images",imgList);
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/only_images")
    @ResponseBody
    public ResponseEntity<?> onlyImages(HttpServletRequest request){
        var body = new HashMap<String, Object>();
        String parentId = directoryService.getCurrentDirectory(request).getId();
        List<Img> imgList = imgService.getImagesOnly(parentId);

        body.put("directories", new ArrayList<Directory>(0));
        body.put("images",imgList);
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

}
