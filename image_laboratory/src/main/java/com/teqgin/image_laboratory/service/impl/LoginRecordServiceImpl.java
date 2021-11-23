package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.LoginRecord;
import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LoginRecordVo;
import com.teqgin.image_laboratory.mapper.LoginRecordMapper;
import com.teqgin.image_laboratory.service.LoginRecordService;
import com.teqgin.image_laboratory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Autowired
    private UserService userService;

    @Override
    public int add(String userId) {
        LoginRecord loginRecord = new LoginRecord(
                IdUtil.getSnowflake().nextIdStr(),
                "1",
                "1",
                "1",
                new Date(),
                userId
        );
        return loginRecordMapper.insert(loginRecord);
    }

    @Override
    public List<LoginRecordVo> getLimit(HttpServletRequest request) {
        return loginRecordMapper.findLoginRecordLimit(userService.getCurrentUser(request).getId());
    }

    @Override
    public List<LoginRecordVo> getAll(HttpServletRequest request) {
        return loginRecordMapper.findAll(userService.getCurrentUser(request).getId());
    }

    @Override
    public int deleteByUserId(String userId) {
        var condition = new QueryWrapper<LoginRecord>();
        condition.eq("user_id",userId);
        return loginRecordMapper.delete(condition);
    }
}
