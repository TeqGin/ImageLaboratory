package com.teqgin.image_laboratory.serviceTest;

import com.teqgin.image_laboratory.service.ImgService;
import com.teqgin.image_laboratory.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImgServiceTest {
    @Autowired
    public ImgService imgService;
    @Autowired
    public RecordService recordService;

/*    @Test
    public void keywordTest(){
        var records = recordService.getRecordsByUser("1102647596@qq.com");
        String keywords = imgService.calculateKeywords(records);
        System.out.println(keywords);
        var urls = imgService.spiderForUrls(keywords);
        urls.forEach(System.out::println);
    }*/
}
