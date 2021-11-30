package com.teqgin.image_laboratory.service;

import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.structure.LabelWeight;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    /**
     * 把base64封装到json中
     * @param result
     * @return
     */
    ResponseEntity<?> turnJsonEntity(String result);

    /**
     * 通过当前文件id获取文件夹下的图片对象
     * @param directoryId
     * @return
     */
    List<Img> getImagesByParentId(String directoryId);

    List<Img> getImagesByParentIdSorted(String parentId, int way);

    List<Img> SearchImagesByParentId(String parentId, String keyword);
    /**
     * 保存图片
     * @param img
     * @return
     */
    int save(Img img);

    /**
     * 根据图片id获取图片对象
     * @param id
     * @return
     */
    Img getById(String id);

    /**
     * 获取用户对应的全部图片
     * @param userId
     * @return
     */
    List<Img> getByUserId(String userId);

    /**
     * 删除图片
     * @param id
     * @param request
     */
    void delete(String id, HttpServletRequest request);

    /**
     * 移动图片
     * @param srcId
     * @param targetId
     * @param request
     */
    void move(String srcId, String targetId, HttpServletRequest request);

    /**
     * 图片推荐
     * @param request
     * @return
     */
    List<String> recommendImage(HttpServletRequest request);

    /**
     * 计算权重
     * @param records
     * @return
     */
    PriorityQueue<LabelWeight> weights(List<LabelInRecordVo> records);

    /**
     * 把本地图片转换成base64编码
     * @param request
     * @param imageId
     * @return
     */
    String turnLocalImageBase64(HttpServletRequest request, String imageId);

    /**
     * 文件树
     * @param model
     * @param request
     */
    void setImageTree2Model(Model model, HttpServletRequest request);

    /**
     * 本地图片图像转文字
     * @param request
     * @param path
     * @return
     * @throws Exception
     */
    Map<String,Object> ocrLocalImage(HttpServletRequest request, String path) throws Exception;

    /**
     * 判断图片是否存在
     * @param path
     * @return
     */
    boolean isImgExist(String path);

    void rename(HttpServletRequest request, String name, String id) throws IOException;

    int deleteByUserId(String userId);


}
