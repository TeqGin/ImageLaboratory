package com.teqgin.image_laboratory.jsonTest;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.service.DirectoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Json {

    @Autowired
    DirectoryService directoryService;
    @Test
    public void jsonTest(){
        JSONArray jsonArray = new JSONArray();
        JSONObject object1 = new JSONObject();
        object1.putOnce("title", "张三");

        JSONObject object = new JSONObject();
        object.putOnce("title", "张三");
        jsonArray.add(object);

        object = new JSONObject();
        object.putOnce("title", "张三");
        jsonArray.add(object);

        object1.putOnce("children",jsonArray);

        System.out.println(object1);
    }

    @Test
    public void treeTest(){
        Directory root = new Directory();
        root.setName("1102647596@qq.com");
        root.setId("1438318939685310464");
        JSONObject treeRoot = directoryService.getTree(root);
        System.out.println(treeRoot);
    }
}
