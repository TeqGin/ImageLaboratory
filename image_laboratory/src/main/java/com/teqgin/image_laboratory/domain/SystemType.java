package com.teqgin.image_laboratory.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemType implements Serializable {
    private String id;

    private String name;
}
