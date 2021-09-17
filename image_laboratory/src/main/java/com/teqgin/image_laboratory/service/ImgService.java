package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Img;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgService {

    String ocr(MultipartFile doc) throws Exception;

    List<Img> getImagesById(String directoryId);
}
