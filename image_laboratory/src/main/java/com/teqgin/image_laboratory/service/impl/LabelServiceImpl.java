package com.teqgin.image_laboratory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Label;
import com.teqgin.image_laboratory.mapper.LabelMapper;
import com.teqgin.image_laboratory.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public boolean isExistsByName(String name) {
        return false;
    }

    @Override
    public Label getOneByName(String name) {
        var condition = new QueryWrapper<Label>();
        condition.eq("name", name);
        return labelMapper.selectOne(condition);
    }

    @Override
    public int addOne(Label label) {
        return labelMapper.insert(label);
    }
}
