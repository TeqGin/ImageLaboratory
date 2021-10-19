package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.structure.LabelWeight;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public interface ImgService {

    /**
     * 将图片转成文字
     * @param doc
     * @return
     * @throws Exception
     */
    String ocr(MultipartFile doc) throws Exception;

    String colourize();

    ResponseEntity<?> turnJsonEntity(String result);

    /**
     * 通过当前文件id获取文件夹下的图片对象
     * @param directoryId
     * @return
     */
    List<Img> getImagesByParentId(String directoryId);

    int save(Img img);

    Img getById(String id);

    List<Img> getByUserId(String userId);

    void delete(String id, HttpServletRequest request);

    void move(String srcId, String targetId, HttpServletRequest request);

    List<String> recommendImage(HttpServletRequest request);

    PriorityQueue<LabelWeight> weights(List<LabelInRecordVo> records);

    String turnLocalImageBase64(HttpServletRequest request, String imageId);

    void setImageTree2Model(Model model, HttpServletRequest request);

    Map<String,Object> ocrLocalImage(HttpServletRequest request, String path) throws Exception;

    boolean isImgExist(String path);
}
