package com.teqgin.image_laboratory.service;

public interface HttpService {
    /**
     * 风格转换代码
     * @param imgStr
     * @param option
     * @return base64
     */
    String styleTrans(String imgStr, String option);
}
