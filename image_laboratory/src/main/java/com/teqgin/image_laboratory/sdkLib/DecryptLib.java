package com.teqgin.image_laboratory.sdkLib;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.teqgin.image_laboratory.Helper.ParameterHelper;
import com.teqgin.image_laboratory.domain.FaceData;


public interface DecryptLib extends Library {
    DecryptLib INSTANCE = (DecryptLib) Native.loadLibrary(ParameterHelper.faceSdkPathStatic, DecryptLib.class);

    FaceData faceTracker(FaceData faceData, String modelPath, String videoPath);
    int safeReleasePointer(Pointer faceData);
}