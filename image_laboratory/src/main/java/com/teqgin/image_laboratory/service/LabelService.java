package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Label;

public interface LabelService {

    boolean isExistsByName(String name);

    Label getOneByName(String name);

    int addOne(Label label);
}
