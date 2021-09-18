package com.teqgin.image_laboratory.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    /**
     * 获取人像信息
     * @param video
     * @return
     */
    String [] getFaces(String video);

    /**
     * 保存mp4文件
     * @param doc
     * @return
     */
    String saveVideo(MultipartFile doc);
}
