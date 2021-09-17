package com.teqgin.image_laboratory.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    String [] getFaces(String video);

    String saveVideo(MultipartFile doc);
}
