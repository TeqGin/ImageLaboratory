package com.teqgin.image_laboratory.pythonTest;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.teqgin.image_laboratory.service.ImgService;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

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

        String url = "localhost:8000/images_split?" + jointToKeyWords();
        String res = HttpUtil.get(url);
        var body = (HashMap<String, Object>)imgService.turnJsonEntity(res).getBody();
        var object = (JSONObject)body.get("img");
        var images = (List<String>)object.getObj("images");
        System.out.println(images.size());
        images.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    @Test
    public void mapTest(){
        System.out.println(jointToKeyWords());
    }

    private String jointToKeyWords(){
        String[] keywords = new String[3];
        String[] backup = new String[]{"猫","狗","鸟"};
        int idx = 0;
        String[] splitWords = "行政区划图 长城 网球 ".split(" ");
        for (int i = 0; i < 3;i++) {
            if (i < splitWords.length && !splitWords[i].equals("")){
                keywords[i] = splitWords[i];
            }else {
                keywords[i] = backup[idx++];
            }
        }
        System.out.println(Arrays.toString(keywords));
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
