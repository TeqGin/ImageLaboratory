package com.teqgin.image_laboratory.UtilTest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;

public class MD5Test {
    @Test
    public void test(){
        System.out.println(SecureUtil.md5("60870736a"));
    }
}
