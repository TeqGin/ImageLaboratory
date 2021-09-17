package com.teqgin.image_laboratory.domain;


import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import lombok.Data;

@Data
public class FaceData extends Structure {
    public int faceNum;
    public int faceDataSize;
    public Pointer faceData;
}
