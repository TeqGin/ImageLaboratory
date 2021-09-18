package com.teqgin.image_laboratory.controller;

import com.baidu.aip.util.Base64Util;
import com.teqgin.image_laboratory.Helper.CodeStatus;
import com.teqgin.image_laboratory.service.HttpService;
import com.teqgin.image_laboratory.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    HttpService httpService;

    @Autowired
    ImgService imageService;


    /**
     * 对图像进行风格化处理并返回图片
     * @param doc 需要进行风格化处理的图片
     * @param option 风格化方式
     * @return
     * @throws IOException
     */
    @PostMapping("/stylize")
    @ResponseBody
    public String stylize(@RequestParam("doc") MultipartFile doc, @RequestParam("option")String option) throws IOException {
        String result = "";
        result = httpService.styleTrans(Base64Util.encode(doc.getBytes()),option);
        return result;
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
     * 获取对于文件夹下的所有图像文件
     * @param directoryId
     * @return
     */
    @PostMapping("/get_images")
    public ResponseEntity<?> getImages(@RequestBody String directoryId){
        var images = imageService.getImagesById(directoryId);
        var body = new HashMap<String, Object>();
        body.put("images", images);
        body.put("code", CodeStatus.SUCCEED);
        body.put("message", "查找文件成功");
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
    public String ocr(){
        return "/image/ocr";
    }

    /**
     * 进入抓取人脸页面
     * @return
     */
    @GetMapping("/catch_face")
    public String catchFace(){
        return "/image/catch_face";
    }

    /**
     * 进入黑白图片上色页面
     * @return
     */
    @GetMapping("/black")
    public String black(){return "/image/blackTurnColor";}

    /**
     * 进入人像动漫化页面
     * @return
     */
    @GetMapping("/cartoon")
    public String cartoon(){return "/image/cartoon";}

    /**
     * 进入神经风格转换页面
     * @return
     */
    @GetMapping("/nst")
    public String nst(){return "/image/nst";}

    /**
     * 进入背景轮廓图页面
     * @return
     */
    @GetMapping("/outline")
    public String outline(){return "/image/outline";}

    /**
     * 图像处理主页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "/image/index";
    }

}
