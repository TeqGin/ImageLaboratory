package com.teqgin.image_laboratory.service.impl;

import cn.hutool.json.JSONUtil;
import com.baidu.aip.ocr.AipOcr;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.Helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.mapper.ImgMapper;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgMapper imgMapper;

    /**
     * 将图片转成文字
     * @param doc
     * @return
     * @throws Exception
     */
    public String ocr(MultipartFile doc) throws Exception {
        AipOcr client = new AipOcr("24381909", "K2EXIgaGValOUrs1PpuMLF2d", "GwERcofS0LhAxCspBBlUyxAxeHB5zkbx");
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>(4);
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        // 参数为二进制数组
        byte[] buf = doc.getBytes();
        JSONObject res = client.basicGeneral(buf, options);

        Map map = TextUtil.json2map(res.toString());
        //  提取并打印出识别的文字
        List list = (List) map.get("words_result");
        int len = ((List) map.get("words_result")).size();
        String str= "";
        for(int i=0; i<len; i++) {
            str = str + ((Map) list.get(i)).get("words") + "\n";
        }
        return str;
    }

    @Override
    public String colourize() {
        return null;
    }

    @Override
    public ResponseEntity<?> turnJsonEntity(String result) {
        var body = new HashMap<String, Object>();

        if(JSONUtil.isJsonObj(result)){
            cn.hutool.json.JSONObject jsonResult =JSONUtil.parseObj(result);
            body.put("img",jsonResult);
            body.put("code", CodeStatus.SUCCEED);
        }else{
            body.put("code", CodeStatus.DATA_ERROR);
        }
        return ResponseEntity.ok(body);
    }

    /**
     * 通过当前文件id获取文件夹下的图片对象
     * @param directoryId
     * @return
     */
    @Override
    public List<Img> getImagesByParentId(String directoryId) {
        var condition = new QueryWrapper<Img>();
        condition.eq("dir_id", directoryId);
        return imgMapper.selectList(condition);
    }
}
