package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.teqgin.image_laboratory.Helper.ParameterHelper;
import com.teqgin.image_laboratory.domain.FaceData;
import com.teqgin.image_laboratory.sdkLib.DecryptLib;
import com.teqgin.image_laboratory.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.xpath.XPath;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    @Value("${upload.path}")
    private String uploadPrefix;

    /**
     * 获取人像信息
     * @param video
     * @return
     */
    @Override
    public String [] getFaces(String path) {
        FaceData faceData = DecryptLib.INSTANCE.faceTracker(new FaceData(), ParameterHelper.modelPathStatic, path);
        String imgString = faceData.faceData.getString(0);
        String [] imgs = null;
        if (faceData.faceDataSize != 0){
            imgs = imgString.split(",");
        }
        //将暂时保存的视频文件删除
        FileUtil.del(path);
        DecryptLib.INSTANCE.safeReleasePointer(faceData.faceData);
        return imgs;
    }

    /**
     * 保存mp4文件
     * @param doc
     * @return
     */
    @Override
    public String saveVideo(MultipartFile doc) {
        File videoFile = new File(StrUtil.format(
                uploadPrefix +"/pic/{}/{}.mp4",
                DateUtil.date().toDateStr(),
                IdUtil.getSnowflake().nextId()));
        FileUtil.mkParentDirs(videoFile);
        try{
            if (!videoFile.exists()){
                videoFile.createNewFile();
            }
            //此处不能直接传入videoFile对象，如果传入的时videoFile对象，那么springboot在查找对于的文件时
            //会加上当前虚拟tomcat的路径，导致文件查找失败，需使用绝对路径
            doc.transferTo(videoFile.getAbsoluteFile());
        }catch (IOException e) {
            log.error("保存视频失败！");
            FileUtil.del(videoFile);
            e.printStackTrace();
        }

        String path = null;
        try{
            path = videoFile.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
