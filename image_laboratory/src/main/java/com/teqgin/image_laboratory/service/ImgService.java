package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Img;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {

    /**
     * 将图片转成文字
     * @param doc
     * @return
     * @throws Exception
     */
    String ocr(MultipartFile doc) throws Exception;

    /**
     * 通过当前文件id获取文件夹下的图片对象
     * @param directoryId
     * @return
     */
    List<Img> getImagesById(String directoryId);
}
