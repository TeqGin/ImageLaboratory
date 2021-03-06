package com.teqgin.image_laboratory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baidu.aip.ocr.AipOcr;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.Record;
import com.teqgin.image_laboratory.domain.vo.LabelInRecordVo;
import com.teqgin.image_laboratory.domain.structure.LabelWeight;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.mapper.ImgMapper;
import com.teqgin.image_laboratory.service.DirectoryService;
import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.RecordService;
import com.teqgin.image_laboratory.service.UserService;
import com.teqgin.image_laboratory.util.FileUtils;
import com.teqgin.image_laboratory.util.RecommendUtil;
import com.teqgin.image_laboratory.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Value("${prefix.python.ip}")
    private String pythonIp;

    @Value("${prefix.python.port}")
    private String pythonPort;

    @Value("${upload.path}")
    private String prefix;

    /**
     * ?????????????????????
     * @param doc
     * @return
     * @throws Exception
     */
    public String ocr(MultipartFile doc) throws Exception {
        return ocrBytes(doc.getBytes());
    }

    public String ocrBytes(byte[] buf) throws Exception {
        AipOcr client = new AipOcr("24381909", "K2EXIgaGValOUrs1PpuMLF2d", "GwERcofS0LhAxCspBBlUyxAxeHB5zkbx");
        // ??????????????????????????????
        HashMap<String, String> options = new HashMap<>(4);
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        // ????????????????????????
        JSONObject res = client.basicGeneral(buf, options);

        Map map = TextUtil.json2map(res.toString());
        //  ?????????????????????????????????
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
     * ??????????????????id?????????????????????????????????
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
    public List<Img> getImagesByParentIdSorted(String parentId, int way) {
        var condition = new QueryWrapper<Img>();
        condition.eq("dir_id", parentId);
        if (way == 0){
            condition.orderByAsc("name");
        }else if (way == 1){
            condition.orderByDesc("name");
        }
        return imgMapper.selectList(condition);
    }

    @Override
    public List<Img> getImagesPublicSorted(int way) {
        if (way == 0){
            return imgMapper.getAllPublicAsc();
        }else if (way == 1){
            return imgMapper.getAllPublicDesc();
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<Img> SearchImagesByParentId(String parentId, String keyword) {
        var condition = new QueryWrapper<Img>();
        condition.eq("dir_id", parentId);
        condition.like("name", keyword);
        return imgMapper.selectList(condition);
    }

    @Override
    public List<Img> SearchImagesPublic(String keyword) {
        return imgMapper.searchPublic(keyword);
    }

    @Override
    public List<Img> getImagesOnly(String parentId) {
        var condition = new QueryWrapper<Img>();
        condition.eq("dir_id", parentId);
        condition.eq("type","image");
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
    public List<Img> getByUserId(String userId) {
        var condition = new QueryWrapper<Img>();
        condition.eq("user_id",userId);
        return imgMapper.selectList(condition);
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
        img.setPath(targetPath + srcName);
        imgMapper.updateById(img);
    }

    @Override
    public List<String> recommendImage(HttpServletRequest request) throws NullPointerException {
        User user = userService.getCurrentUser(request);
        var records = recordService.getRecordsByUser(user.getId());
        String keywords = calculateKeywords(records);
        log.info("???????????????????????? " + keywords);
        return spiderForUrls(keywords).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> recommendImageByKeyword(String keyword) {
        log.info("???????????????????????? " + keyword);
        return spiderOneForUrls(keyword).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public String turnLocalImageBase64(HttpServletRequest request, String imageId) {
        Img img = getById(imageId);
        return FileUtils.GetImageStr(img.getPath());
    }

    @Override
    public void setImageTree2Model(Model model, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        if (user == null){
            model.addAttribute("status", "-1");
        }else{
            model.addAttribute("status", "0");
            model.addAttribute("tree", directoryService.getImageTree(directoryService.getRootDirectory(user.getAccount())));
        }
    }

    @Override
    public Map<String,Object> ocrLocalImage(HttpServletRequest request, String path) throws Exception {
        Map<String,Object> body = new HashMap<>(2);
        File img = new File(path);
        byte[] buf;
        try(FileInputStream fis = new FileInputStream(img)){
            buf = new byte[fis.available()];
            fis.read(buf);
        }

        body.put("base64", Base64.encodeBase64String(buf));
        body.put("content", ocrBytes(buf));
        return body;
    }

    @Override
    public boolean isImgExist(String path) {
        var condition = new QueryWrapper<Img>();
        condition.eq("path", path);
        return imgMapper.selectOne(condition) != null;
    }

    @Override
    public void rename(HttpServletRequest request, String name, String id) throws IOException {
        Img old = getById(id);
        String oldPath = old.getPath();
        File oldFile = new File(oldPath);
        FileUtil.rename(oldFile,name,true);
        old.setName(name);
        old.setPath(directoryService.getFullPath(old.getDirId())+ "/" + old.getName());
        imgMapper.updateById(old);
    }

    @Override
    public int deleteByUserId(String userId) {
        var condition = new QueryWrapper<Img>();
        condition.eq("user_id",userId);
        return imgMapper.delete(condition);
    }

    @Override
    public String getImageBaseById(String imageId) {
        Img img = imgMapper.selectById(imageId);
        String path = img.getPath();
        return FileUtils.GetImageStr(path);
    }

    @Override
    public String getPublicImageBaseById(String imageId) {
        Img img = imgMapper.selectPublicById(imageId);
        String path = img.getPath();
        return FileUtils.GetImageStr(path);
    }

    @Override
    public List<Img> getSharedImages() {
        return imgMapper.getSharedImages();
    }

    @Override
    public boolean isPublic(String id) {
        return imgMapper.isPublic(id) == 1;
    }

    @Override
    @Transactional
    public void makePublic(String id) throws IOException {
        Img self = imgMapper.selectById(id);
        Img shared = new Img();
        BeanUtil.copyProperties(self,shared);

        shared.setId(IdUtil.getSnowflake().nextIdStr());
        shared.setInsertDate(new Date());
        shared.setPath(createPublicPath(shared.getName()));

        FileUtil.mkParentDirs(shared.getPath());
        File target = new File(shared.getPath());
        if (!target.exists()){
            target.createNewFile();
        }
        FileUtil.copy(self.getPath(), shared.getPath(), true);

        imgMapper.makePublic(id);
        imgMapper.insertPublic(shared);
        imgMapper.buildRelation(IdUtil.getSnowflake().nextIdStr(),
                self.getId(),
                shared.getId());
    }

    @Override
    public Img getPublicImage(String id) {
        return imgMapper.selectPublicById(id);
    }

    @Override
    public void deletePublicImage(String id) {
        imgMapper.deletePublicImageById(id);
        imgMapper.changePublic(id);
        imgMapper.deleteRelation(id);
    }

    @Override
    public boolean isPublicImageOwner(HttpServletRequest request, String id) {
        User user = userService.getCurrentUser(request);
        Img img = imgMapper.selectPublicById(id);
        return user.getId().equals(img.getUserId());
    }

    @Override
    public void publicRename(String name, String id) {
        Img old = imgMapper.selectPublicById(id);
        String oldPath = old.getPath();
        File oldFile = new File(oldPath);
        FileUtil.rename(oldFile,name,true);
        String path = createPublicPath(name);
        imgMapper.changePublicImageName(name, id, path);
    }

    @Override
    public void makePrivate(String id, HttpServletRequest request) throws IOException {
        Img publicImage = imgMapper.selectPublicById(id);
        String path = publicImage.getPath();

        userService.saveTransferredImage(request, FileUtils.GetImageStr(path));
    }

    public String createPublicPath(String fileName){
        StringBuilder path = new StringBuilder();
        path.append(prefix);
        path.append("/");
        path.append("public");
        path.append("/");
        path.append(fileName);
        return path.toString();
    }

    @Override
    public  List<LabelWeight> weights(List<LabelInRecordVo> records){
        List<LabelWeight> labelWeights = new ArrayList<>();
        for (var record: records) {
            double weight = RecommendUtil.countImageWeight(record.getUpdateDate(),record.getCount());
            labelWeights.add(new LabelWeight(weight,record.getLabelName()));
        }
        return labelWeights;
    }

    private String calculateKeywords(List<LabelInRecordVo> records){
        List<LabelWeight> collect = weights(records);
        collect.sort((l1, l2) -> (int) (l2.weight - l1.weight));
        StringBuilder keywords = new StringBuilder();
        int i = 0;
        for (LabelWeight l: collect) {
            keywords.append(l.name + " ");
            if (++i >=3 ){
                break;
            }
        }
        return keywords.toString();
    }
    private List<String> spiderForUrls(String keywords) throws NullPointerException{

        // ??????????????? localhost:8080/image_split?key1=%E7%8C%AB&key2=%E7%8B%97&key3=%E9%B8%9F???url
        String url = pythonIp + ":" + pythonPort +"/images_split?" + jointPassVal(keywords);
        log.info("???python??????url???????????????????????????" + url);
        String res = HttpUtil.get(url);

        var body = (HashMap<String, Object>)turnJsonEntity(res).getBody();
        var object = (cn.hutool.json.JSONObject)body.get("img");
        var urls = (List<String>)object.getObj("images");
        return urls;
    }

    private List<String> spiderOneForUrls(String keyword) throws NullPointerException{

        // ??????????????? localhost:8080/image_split?key1=%E7%8C%AB&key2=%E7%8B%97&key3=%E9%B8%9F???url
        String url = pythonIp + ":" + pythonPort +"/images?keywords=" + URLUtil.encode(keyword);
        log.info("???python??????url???????????????????????????" + url);
        String res = HttpUtil.get(url);

        var body = (HashMap<String, Object>)turnJsonEntity(res).getBody();
        var object = (cn.hutool.json.JSONObject)body.get("img");
        var urls = (List<String>)object.getObj("images");
        return urls;
    }

    private String jointPassVal(String row){
        String[] keywords = new String[3];
        String[] backup = new String[]{"???","???","???"};
        int idx = 0;
        String[] splitWords = row.split(" ");
        for (int i = 0; i < 3;i++) {
            if (i < splitWords.length && !splitWords[i].equals("")){
                keywords[i] = splitWords[i];
            }else {
                keywords[i] = backup[idx++];
            }
        }
        LinkedList<Integer> nums = new LinkedList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        Iterator<Integer> iterator = nums.iterator();
        return Arrays.stream(keywords)
                .map(s -> "key" + iterator.next() + "=" + URLUtil.encode(s))
                .collect(Collectors.joining("&"));

    }
}
