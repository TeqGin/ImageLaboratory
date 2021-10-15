package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.Label;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.domain.structure.LabelWeight;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import com.teqgin.image_laboratory.mapper.LabelMapper;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.LabelService;
import com.teqgin.image_laboratory.service.RecordService;
import com.teqgin.image_laboratory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImgService imgService;

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

    @Override
    public List<Object> graphData(HttpServletRequest request) {
        List<LabelInRecordVo> labelRecords = recordService.getRecordsByUser(userService.getCurrentUser(request).getId());
        List<String> labelNames = labelRecords.stream().map(LabelInRecordVo::getLabelName).collect(Collectors.toList());
        List<Integer> counts = labelRecords.stream().map(LabelInRecordVo::getCount).collect(Collectors.toList());
        List<Object> body = new ArrayList<>(2);
        body.add(labelNames);
        body.add(counts);
        return body;
    }

    @Override
    public List<Map<String, Object>> calculatePreference(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        var records = recordService.getRecordsByUser(user.getId());
        PriorityQueue<LabelWeight> labelWeights = imgService.weights(records);
        List<Map<String,Object>> preference = new ArrayList<>(10);
        for (LabelWeight labelWeight : labelWeights) {
            Map<String,Object> e = new HashMap<>(2);
            e.put("value",labelWeight.weight);
            e.put("name", labelWeight.name);
            preference.add(e);
        }
        return preference;
    }

    @Override
    public List<Object> uploadAnalyze(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        List<Img> imgs = imgService.getByUserId(user.getId());
        Map<String,Integer> dayMap = new HashMap<>();

        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        for (Img img : imgs) {
            int day = DateUtil.dayOfWeek(img.getInsertDate());
            String dayString = weeks[day - 1];
            dayMap.put(dayString, dayMap.getOrDefault(dayString,0) + 1);
        }

        List<Object> uploadNum = new ArrayList<>(2);
        uploadNum.add(dayMap.keySet());
        uploadNum.add(dayMap.values());

        return uploadNum;
    }
}
