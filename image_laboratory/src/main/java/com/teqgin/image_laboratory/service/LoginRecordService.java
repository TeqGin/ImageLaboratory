package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.vo.LoginRecordVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginRecordService {

    /**
     * 插入到数据库中
     * @param userId
     * @return
     */
    int add(String userId);

    /**
     * 获取前三条
     * @param request
     * @return
     */
    List<LoginRecordVo> getLimit(HttpServletRequest request);

    /**
     * 获取全部
     * @param request
     * @return
     */
    List<LoginRecordVo> getAll(HttpServletRequest request);
}
