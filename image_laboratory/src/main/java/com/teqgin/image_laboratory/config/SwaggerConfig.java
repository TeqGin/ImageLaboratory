package com.teqgin.image_laboratory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment){
        Profiles profiles = Profiles.of("dev","test");
        // 判断当前环境是否是开发或测试环境
        boolean enable = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否启用swagger
                .enable(enable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.teqgin.image_laboratory.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 合作开发配置多组api
     * @return
     */
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("zhangsan");
    }
    private ApiInfo apiInfo(){
        // 作者信息
        Contact contact = new Contact("teqgggggin","","");
        return  new ApiInfo(
                "TeqGin's 图像站的Api Documentation",
                "Api Documentation",
                "v1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
