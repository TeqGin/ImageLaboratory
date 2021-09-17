package com.teqgin.image_laboratory.FileTest;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class FolderTest {

    /**
     * succeed
     * */
    @Test
    public void rename(){
        File file = new File("/forChange");
        File file1 = new File("/after");
        file.renameTo(file1);
    }
    @Test
    public void create(){
        String path = "/pot/server/image/my/";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        path += "hello//";
        file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }
}
