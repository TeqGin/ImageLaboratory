package com.teqgin.image_laboratory.controller;

import com.teqgin.image_laboratory.Helper.CodeStatus;
import com.teqgin.image_laboratory.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@RequestMapping("/video")
@Slf4j
public class VideoController {
    @Autowired
    VideoService videoService;

    /**
     * 使用人像sdk抓取视频中的人像图片
     * @param video
     * @return
     */
    @PostMapping(value = "/catch",consumes = "multipart/form-data")
    public ResponseEntity<?> catchFace(@RequestParam("doc") MultipartFile video){
        String path = videoService.saveVideo(video);
        var body = new HashMap<String,Object>();

        if (path == null){
            body.put("code", CodeStatus.DATA_ERROR);
            body.put("message", "保存文件失败，无法进行人脸识别");
            return ResponseEntity.ok(body);
        }
        //获取人像
        String [] images = videoService.getFaces(path);
        body.put("images", images);
        body.put("message", "获取人像成功!");
        body.put("code", CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }
}
