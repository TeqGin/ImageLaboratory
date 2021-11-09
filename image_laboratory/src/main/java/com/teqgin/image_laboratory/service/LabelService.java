package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Label;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface LabelService {

    /**
     * 判断标签是否存在
     * @param name
     * @return
     */
    boolean isExistsByName(String name);

    /**
     * 通过标签名获得标签对象
     * @param name
     * @return
     */
    Label getOneByName(String name);

    /**
     * 插入标签到数据库中
     * @param label
     * @return
     */
    int addOne(Label label);

    /**
     * 柱状图数据
     * @param request
     * @return
     */
    List<Object> graphData(HttpServletRequest request);

    /**
     * 计算偏好数据
     * @param request
     * @return
     */
    List<Map<String, Object>> calculatePreference(HttpServletRequest request);

    /**
     * 上传分析
     * @param request
     * @return
     */
    List<Object> uploadAnalyze(HttpServletRequest request);
}
