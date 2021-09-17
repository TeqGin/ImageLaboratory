package com.teqgin.image_laboratory.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

public class TextUtil {
    public static String mail(String code){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <STYLE>\n" +
                "        p{\n" +
                "            text-align: center\n" +
                "        }\n" +
                "        #code{\n" +
                "            padding-top: 10px;\n" +
                "            color: rgba(0, 0, 0, 0.49);\n" +
                "        }\n" +
                "        .code_bg{\n" +
                "            width: 100%;\n" +
                "            height: 50px;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "    </STYLE>\n" +
                "</head>\n" +
                "<body>\n" +
                "<br>" +"<br>" +
                "<p >你好，我是<em>TeqGin's image station</em>，你的验证码如下，请查收,验证码十分钟后失效，请尽快修改密码：</p>\n" +
                "<p >Copy and paste this code to verify, the code will out of work after ten minutes:</p>\n" +
                "<div class=\"code_bg\">\n" +
                "    <p id=\"code\">" + code + "</p>\n" +
                "</div>\n" +
                "\n" +
                 "<br>" +"<br>" +"<br>" +"<br>" +"<br>" +"<br>" +
                "</body>\n" +
                "</html>";
    }

    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }

    public static String getVerifyCode(){
        String code = UUID.randomUUID().toString().replace("-","").substring(0,6);
        return code;
    }
}
