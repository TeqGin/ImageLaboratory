package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Label;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface LabelService {

    boolean isExistsByName(String name);

    Label getOneByName(String name);

    int addOne(Label label);

    List<Object> graphData(HttpServletRequest request);

    List<Map<String, Object>> calculatePreference(HttpServletRequest request);

    List<Object> uploadAnalyze(HttpServletRequest request);
}
