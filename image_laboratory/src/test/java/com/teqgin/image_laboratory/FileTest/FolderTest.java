package com.teqgin.image_laboratory.FileTest;


import cn.hutool.core.io.FileUtil;
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
    @Test
    public void delete(){
        create();
        File file = new File("/pot");
        FileUtil.del(file);
    }
    @Test
    public void move(){
        FileUtil.move(new File("/pot/server/image/hello"), new File("/pot/server/image/my"), false);
    }
}
