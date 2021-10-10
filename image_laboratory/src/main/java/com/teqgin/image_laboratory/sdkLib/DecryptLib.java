package com.teqgin.image_laboratory.sdkLib;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.teqgin.image_laboratory.helper.ParameterHelper;
import com.teqgin.image_laboratory.domain.FaceData;


public interface DecryptLib extends Library {
    DecryptLib INSTANCE = (DecryptLib) Native.loadLibrary(ParameterHelper.faceSdkPathStatic, DecryptLib.class);

    /**
     * 获取人像数据
     * @param faceData
     * @param modelPath
     * @param videoPath
     * @return
     */
    FaceData faceTracker(FaceData faceData, String modelPath, String videoPath);

    /**
     * 释放空间
     * @param faceData
     * @return
     */
    int safeReleasePointer(Pointer faceData);
}