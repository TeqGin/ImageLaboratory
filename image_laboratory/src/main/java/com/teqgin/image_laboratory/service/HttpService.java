package com.teqgin.image_laboratory.service;

public interface HttpService {
    /**
     * 风格转换代码
     * @param imgStr
     * @param option
     * @return base64
     */
    String styleTrans(String imgStr, String option);

    /**
     * 黑白图像上色
     */
    String colorize(String imgStr);

    /**
     * 人像动漫化
     * @param imgStr
     * @return
     */
    String selfieAnime(String imgStr);

    /**
     * 天空分割
     * @param imgStr
     * @return
     */
    String skySeg(String imgStr);

    String advancedGeneral(String imgStr);

    String getTag(String imgStr);

    String dehaze(String imgStr);

    String contrast_enhance(String imgStr);

    String image_quality_enhance(String imgStr);

    String stretchRestore(String imgStr);

    String image_definition_enhance(String imgStr);

    String colorEnhance(String imgStr);
}
