package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Img;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ImgService {

    /**
     * 将图片转成文字
     * @param doc
     * @return
     * @throws Exception
     */
    String ocr(MultipartFile doc) throws Exception;

    String colourize();

    ResponseEntity<?> turnJsonEntity(String result);

    /**
     * 通过当前文件id获取文件夹下的图片对象
     * @param directoryId
     * @return
     */
    List<Img> getImagesByParentId(String directoryId);

    int save(Img img);

    Img getById(String id);

    void delete(String id, HttpServletRequest request);
}
