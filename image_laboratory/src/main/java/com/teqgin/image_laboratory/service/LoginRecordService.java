package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.vo.LoginRecordVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginRecordService {

    int add(String userId);

    List<LoginRecordVo> getLimit(HttpServletRequest request);

    List<LoginRecordVo> getAll(HttpServletRequest request);
}
