package com.teqgin.image_laboratory.service.impl;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.teqgin.image_laboratory.service.HttpService;
import com.teqgin.image_laboratory.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
@Service
@Slf4j
public class HttpServiceImpl implements HttpService {

    /**
     * 获取权限token
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "K2EXIgaGValOUrs1PpuMLF2d";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "GwERcofS0LhAxCspBBlUyxAxeHB5zkbx";
        return getAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            log.error("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            log.error("获取token失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 风格转换代码
     * @param imgStr
     * @param option
     * @return base64
     */
    public String styleTrans(String imgStr, String option) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/style_trans";
        try {
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "option=" + option + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            log.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 黑白图像上色
     */
    public String colorize(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/colourize";
        try {
            // 本地文件路径
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            log.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selfieAnime(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/selfie_anime";
        try {
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String skySeg(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-process/v1/sky_seg";
        try {
            // 本地文件路径
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            log.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String advancedGeneral(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
        try {
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAuth("NzdWcaSxGA9RSxeanspGoSfQ", "wmi6tLDWjoj4xaWPOWfzxNvWFESAsMWS ");

            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTag(String imgStr) {
        String res = advancedGeneral(imgStr);
        // 把json字符串转成hutool json对象
        cn.hutool.json.JSONObject jsonResult = JSONUtil.parseObj(res);

        //获取首个关键词
        if (jsonResult.get("result") instanceof JSONArray){
            JSONArray result = (JSONArray) jsonResult.get("result");

            if (result.get(0) instanceof cn.hutool.json.JSONObject){
                cn.hutool.json.JSONObject firstValue = (cn.hutool.json.JSONObject) result.get(0);

                return firstValue.get("keyword").toString();
            }
        }
        return null;
    }
}
