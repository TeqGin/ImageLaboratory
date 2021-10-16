package com.teqgin.image_laboratory.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.baidu.aip.util.Base64Util;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.service.*;
import com.teqgin.image_laboratory.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private HttpService httpService;

    @Autowired
    private ImgService imageService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;
    @Autowired
    private DirectoryService directoryService;

    @Value("${prefix.python.ip}")
    private String pythonIp;

    @Value("${prefix.python.port}")
    private String pythonPort;

    /**
     * 对图像进行风格化处理并返回图片
     * @param doc 需要进行风格化处理的图片
     * @param option 风格化方式
     * @return
     * @throws IOException
     */
    @PostMapping("/stylize")
    @ResponseBody
    public ResponseEntity<?> stylize(@RequestParam("doc") MultipartFile doc, @RequestParam("option")String option) throws IOException {
        String result = "";
        result = httpService.styleTrans(Base64Util.encode(doc.getBytes()),option);
        return imageService.turnJsonEntity(result);
    }

    @PostMapping("/local_stylize")
    @ResponseBody
    public ResponseEntity<?> localStylize(@RequestParam("path") String path, @RequestParam("option")String option) throws IOException {
        String result = "";
        result = httpService.styleTrans(FileUtils.GetImageStr(path),option);
        return imageService.turnJsonEntity(result);
    }

    /**
     * 图像上色
     * @param doc
     * @return
     * @throws IOException
     */

    @PostMapping("/colorize")
    @ResponseBody
    public ResponseEntity<?> colorize(@RequestParam("doc") MultipartFile doc) throws IOException {
        String result = "";
        result = httpService.colorize(Base64Util.encode(doc.getBytes()));
        return imageService.turnJsonEntity(result);
    }

    @PostMapping("/local_colorize")
    @ResponseBody
    public ResponseEntity<?> localColorize(@RequestParam("path") String path) throws IOException {
        String result = "";
        result = httpService.colorize(FileUtils.GetImageStr(path));
        return imageService.turnJsonEntity(result);
    }

    /**
     * 人像动漫化
     * @param doc
     * @return
     * @throws IOException
     */

    @PostMapping("/selfie_anime")
    @ResponseBody
    public ResponseEntity<?> selfieAnime(@RequestParam("doc") MultipartFile doc) throws IOException {
        String result = "";
        result = httpService.selfieAnime(Base64Util.encode(doc.getBytes()));
        return imageService.turnJsonEntity(result);
    }

    @PostMapping("/local_selfie_anime")
    @ResponseBody
    public ResponseEntity<?> localSelfieAnime(@RequestParam("path") String path) throws IOException {
        String result = "";
        result = httpService.selfieAnime(FileUtils.GetImageStr(path));
        return imageService.turnJsonEntity(result);
    }

    @PostMapping("/sky_seg")
    @ResponseBody
    public ResponseEntity<?> skySeg(@RequestParam("doc") MultipartFile doc) throws IOException {
        //将图片保存到本地
        String path = videoService.saveTempFile(doc);
        path = path.replace("\\","/");
        //发送http请求
        String url = pythonIp + ":" + pythonPort +"/segment/?path=" + path;
        log.info("向python发送url请求，请求地址为：" + url);
        String res = HttpUtil.get(url);
        //删除图片
        File image = new File(path);
        FileUtil.del(image);
        return imageService.turnJsonEntity(res);
    }

    @PostMapping("/local_sky_seg")
    @ResponseBody
    public ResponseEntity<?> localSkySeg(@RequestParam("path") String path) throws IOException {

        path = path.replace("\\","/");
        //发送http请求
        String url = pythonIp + ":" + pythonPort +"/segment/?path=" + path;
        log.info("向python发送url请求，请求地址为：" + url);
        String res = HttpUtil.get(url);

        return imageService.turnJsonEntity(res);
    }

    /**
     * 提取图片中的文字，并返回
     * */
    @PostMapping("/ocr_image")
    @ResponseBody
    public ResponseEntity<?> ocrImage(@RequestParam("doc") MultipartFile doc) throws Exception {
        String text = imageService.ocr(doc);
        var body = new HashMap<String, Object>();
        body.put("content",text);
        body.put("code", CodeStatus.SUCCEED);

        return ResponseEntity.ok(body);
    }


    /**
     * 提取图片中的文字，并返回
     * */
    @PostMapping("/ocr_local_image")
    @ResponseBody
    public ResponseEntity<?> ocrLocalImage(HttpServletRequest request, @RequestParam("path")String path){
        var body = new HashMap<String, Object>(3);
        try{
            Map<String,Object> imageInfo = imageService.ocrLocalImage(request,path);
            body.putAll(imageInfo);
            body.put("code", CodeStatus.SUCCEED);
        }catch (Exception e) {
            e.printStackTrace();
            body.put("code", CodeStatus.UNKNOWN_ERROR);
        }

        return ResponseEntity.ok(body);
    }

    /**
     * 获取对于文件夹下的所有图像文件
     * @param directoryId
     * @return
     */
    @PostMapping("/get_images")
    public ResponseEntity<?> getImages(@RequestBody String directoryId){
        var images = imageService.getImagesByParentId(directoryId);
        var body = new HashMap<String, Object>();
        body.put("images", images);
        body.put("code", CodeStatus.SUCCEED);
        body.put("message", "查找文件成功");
        return ResponseEntity.ok(body);
    }


    @PostMapping("/show_local_image")
    public ResponseEntity<?> showLocalImage(HttpServletRequest request, @RequestParam String imageId){
        String base64 = imageService.turnLocalImageBase64(request, imageId);
        var body = new HashMap<String, Object>();
        body.put("base64", base64);
        body.put("code", CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    /**
     * 进入风格化主页
     * @return
     */
    @GetMapping("/change")
    public String change(){
        return "/image/change";
    }

    /**
     * 进入图像转文字主页
     * @return
     */
    @GetMapping("/ocr")
    public String ocr(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/ocr";
    }

    /**
     * 进入抓取人脸页面
     * @return
     */
    @GetMapping("/catch_face")
    public String catchFace(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/catch_face";
    }

    /**
     * 进入黑白图片上色页面
     * @return
     */
    @GetMapping("/black")
    public String black(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/blackTurnColor";
    }

    /**
     * 进入人像动漫化页面
     * @return
     */
    @GetMapping("/cartoon")
    public String cartoon(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/cartoon";
    }

    /**
     * 进入神经风格转换页面
     * @return
     */
    @GetMapping("/nst")
    public String nst(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/nst";
    }

    /**
     * 进入背景轮廓图页面
     * @return
     */
    @GetMapping("/outline")
    public String outline(Model model, HttpServletRequest request){
        imageService.setImageTree2Model(model, request);
        return "/image/outline";
    }

    /**
     * 图像处理主页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "/image/index";
    }

}
