package com.teqgin.image_laboratory.util;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @Description: 文件工具类
 * @Author: cqr
 * @Date 2020/12/2 16:42
 * @Version 1.0
 */
@Slf4j
public class FileUtils {
    public static File create(String filePath) {
        return create(new File(filePath));
    }

    public static File create(File file) {
        try {
            file.mkdirs();
            file.delete();
            if (!file.createNewFile()) {
                throw new RuntimeException(StrUtil.format("文件{}创建失败", file.getCanonicalPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) throws IOException {
        create("json/s/json1.json");
        create("json/s/json2.json");
        create("json/json.json");
        create("json.json");
    }

    //图片转化成base64字符串
    public static String GetImageStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        //读取图片字节数组
        try (InputStream in = new FileInputStream(imgFile);) {
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            log.info("Base64Util GetImageStr e:" + e.getLocalizedMessage());
        }
        //返回Base64编码过的字节数组字符串
        return Base64.encodeBase64String(data);
    }

    //base64字符串转化成图片
    public static String base642Image(String imgStr, File dest) throws IOException {
        //对字节数组字符串进行Base64解码并生成图片
        String imgUrl = "";
        //图像数据为空
        if (StringUtils.isBlank(imgStr))
            return imgUrl;
        //Base64解码
        byte[] b = Base64.decodeBase64(imgStr);
        for (int i = 0; i < b.length; ++i) {
            //调整异常数据
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        //文件最终的存储位置
        FileUtil.mkParentDirs(dest);
        //判断目标文件所在的目录是否存在
        if (!dest.getParentFile().exists()) {
            //创建目录
            if (!dest.getParentFile().mkdirs()) {
                return imgUrl;
            }
        }
        //新生成的图片
        imgUrl = dest.getCanonicalPath();
        //这种写法不需要flush或者close 会自动关闭 FileOutputStream 实现了java中的java.lang.AutoCloseable接口。
        try (OutputStream out = new FileOutputStream(imgUrl)) {
            out.write(b);
        } catch (Exception e) {
            throw e;
        }

        return imgUrl;
    }
}

