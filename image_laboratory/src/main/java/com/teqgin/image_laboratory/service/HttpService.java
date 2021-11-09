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

    /**
     * 图片分析
     * @param imgStr
     * @return
     */
    String advancedGeneral(String imgStr);

    /**
     * 获得图片标签
     * @param imgStr
     * @return
     */
    String getTag(String imgStr);

    /**
     * 去雾
     * @param imgStr
     * @return
     */
    String dehaze(String imgStr);

    /**
     * 对比度增强
     * @param imgStr
     * @return
     */
    String contrast_enhance(String imgStr);

    /**
     * 图像无损放大
     * @param imgStr
     * @return
     */
    String image_quality_enhance(String imgStr);

    /**
     * 拉伸恢复
     * @param imgStr
     * @return
     */
    String stretchRestore(String imgStr);

    /**
     * 图像清晰度增强
     * @param imgStr
     * @return
     */
    String image_definition_enhance(String imgStr);

    /**
     * 色彩增强
     * @param imgStr
     * @return
     */
    String colorEnhance(String imgStr);
}
