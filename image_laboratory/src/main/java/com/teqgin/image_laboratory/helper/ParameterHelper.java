package com.teqgin.image_laboratory.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ParameterHelper {
    @Value("${face.detect.model}")
    private String modelPath;

    @Value("${face.detect.sdk}")
    private String faceSdkPath;

    public static String modelPathStatic;

    public static String faceSdkPathStatic;

    @PostConstruct
    public void init(){
        ParameterHelper.modelPathStatic = this.modelPath;
        ParameterHelper.faceSdkPathStatic = this.faceSdkPath;
    }
}
