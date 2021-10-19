package com.teqgin.image_laboratory.domain;


import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.Data;

import java.io.Serializable;

@Data
public class FaceData extends Structure implements Serializable {
    public int faceNum;
    public int faceDataSize;
    public Pointer faceData;
}
