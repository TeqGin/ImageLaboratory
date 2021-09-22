package com.teqgin.image_laboratory.controller;


import com.teqgin.image_laboratory.Helper.CodeStatus;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
     * @param directoryId
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam String id,@RequestParam int isDirectory,HttpServletRequest request){
        var body = new HashMap<String, Object>();
        if (isDirectory == 1){
            directoryService.delete(id,request);
        }else if (isDirectory == 2){
            imgService.delete(id,request);
        }else {
            body.put("code",CodeStatus.DATA_ERROR);
            return ResponseEntity.ok(body);
        }
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);

    }

    /**
     * 返回上级或根目录，根据root的值进行判断，
     * @param request
     * @param root 0:返回上级目录，1:返回根目录
     * @return
     */
    @PostMapping("/back")
    @ResponseBody
    public ResponseEntity<?> backDirectory(HttpServletRequest request){
        if (directoryService.isAtRoot(request)){
            var body = new HashMap<String, Object>();
            body.put("code",CodeStatus.NO_CHANGE);
            return ResponseEntity.ok(body);
        }
        String id = directoryService.backLastDirectory(request);
        var directories = directoryService.getChildDirectory(id);
        var body = new HashMap<String, Object>();
        body.put("directories", directories);
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/rename")
    public ResponseEntity<?> rename(@RequestParam("name")String name,@RequestParam("id")String directoryId,HttpServletRequest request){
        directoryService.rename(request,name,directoryId);
        var body = new HashMap<String, Object>();
        body.put("code",CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/move")
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

}
