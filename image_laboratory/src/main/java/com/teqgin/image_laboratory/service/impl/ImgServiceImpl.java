package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baidu.aip.ocr.AipOcr;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.dbVo.LabelInRecord;
import com.teqgin.image_laboratory.domain.structure.LabelWeight;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.mapper.ImgMapper;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.RecordService;
import com.teqgin.image_laboratory.service.UserService;
import com.teqgin.image_laboratory.util.RecommendUtil;
import com.teqgin.image_laboratory.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Slf4j
public class ImgServiceImpl implements ImgService {

    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private RecordService recordService;

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

    @Override
    public int save(Img img) {
        return imgMapper.insert(img);
    }

    @Override
    public Img getById(String id) {
        return imgMapper.selectById(id);
    }

    @Override
    public void delete(String id, HttpServletRequest request) {
        Img img = imgMapper.selectById(id);
        String path = directoryService.getFullPath(img.getDirId()) + "/" + img.getName();
        File file = new File(path);
        FileUtil.del(file);
        imgMapper.deleteById(img.getId());
    }

    @Override
    public void move(String srcId, String targetId, HttpServletRequest request) {
        Img img = imgMapper.selectById(srcId);

        String srcName = img.getName();
        String srcPath = directoryService.getCurrentPath(request) + "/" + srcName;
        String targetPath = directoryService.getFullPath(targetId);
        FileUtil.move(new File(srcPath), new File(targetPath), false);
        img.setDirId(targetId);
        imgMapper.updateById(img);
    }

    @Override
    public List<String> recommendImage(HttpServletRequest request) throws NullPointerException {
        User user = userService.getCurrentUser(request);
        var records = recordService.getRecordsByUser(user.getAccount());
        String keywords = calculateKeywords(records);
        return spiderForUrls(keywords);
    }

    private String calculateKeywords(List<LabelInRecord> records){
        PriorityQueue<LabelWeight> labelWeights = new PriorityQueue<>((l1,l2)-> (int) (l2.weight - l1.weight));
        for (var record: records) {
            double weight = RecommendUtil.countImageWeight(record.getUpdateDate(),record.getCount());
            labelWeights.add(new LabelWeight(weight,record.getLabelName()));
        }
        StringBuilder keywords = new StringBuilder();
        int i = 0;
        for (LabelWeight l: labelWeights) {
            keywords.append(l.name + " ");
            if (++i >=3 ){
                break;
            }
        }
        return keywords.toString();
    }
    private List<String> spiderForUrls(String keywords) throws NullPointerException{
        keywords = URLUtil.encode(keywords);
        String url = "localhost:8000/images?keywords=" + keywords;
        String res = HttpUtil.get(url);
        var body = (HashMap<String, Object>)turnJsonEntity(res).getBody();
        var object = (cn.hutool.json.JSONObject)body.get("img");
        var urls = (List<String>)object.getObj("images");
        return urls;
    }

}
