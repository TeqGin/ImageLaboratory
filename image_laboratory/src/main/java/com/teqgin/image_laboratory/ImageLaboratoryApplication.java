package com.teqgin.image_laboratory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.teqgin.image_laboratory.mapper")
public class ImageLaboratoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageLaboratoryApplication.class, args);
    }

}
