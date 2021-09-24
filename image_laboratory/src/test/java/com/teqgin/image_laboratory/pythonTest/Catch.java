package com.teqgin.image_laboratory.pythonTest;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.teqgin.image_laboratory.service.ImgService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class Catch {
    @Autowired
    ImgService imgService;

    @Test
    public void catchCat(){
        String keywords = "猫 狗 鸟";
        keywords = URLUtil.encode(keywords);
/*        String url = "localhost:8000/segment/?path=" + "aa";
        String res = HttpUtil.get(url);*/
        String url = "localhost:8000/images?keywords=" + keywords;
        String res = HttpUtil.get(url);
        var body = (HashMap<String, Object>)imgService.turnJsonEntity(res).getBody();
        var object = (JSONObject)body.get("img");
        var images = (List<String>)object.getObj("images");
        images.forEach(System.out::println);
    }
}
