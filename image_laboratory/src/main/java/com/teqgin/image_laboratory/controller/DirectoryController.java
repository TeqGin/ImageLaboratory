package com.teqgin.image_laboratory.controller;


import com.teqgin.image_laboratory.Helper.CodeStatus;

import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.dto.StringDto;
import com.teqgin.image_laboratory.exception.FileCreateFailureException;
import com.teqgin.image_laboratory.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @PostMapping("/next")
    @ResponseBody
    public ResponseEntity<?> next(HttpServletRequest request, @RequestBody String directoryId){
        var directories = directoryService.getChildDirectory(directoryId);
        directoryService.enterNextDirectory(directoryId, request);

        var body = new HashMap<String, Object>();
        body.put("directories", directories);
        body.put("code", CodeStatus.SUCCEED);
        body.put("message", "查找文件夹成功");
        return ResponseEntity.ok(body);
    }

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

    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteDirectory(@RequestBody String directoryId){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/back")
    @ResponseBody
    public ResponseEntity<?> backDirectory(HttpServletRequest request, int root){
        String id = directoryService.backLastDirectory(request);
        var directories = directoryService.getChildDirectory(id);
        var body = new HashMap<String, Object>();
        body.put("directories", directories);

        return ResponseEntity.ok(body);
    }

}
