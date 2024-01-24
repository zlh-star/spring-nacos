package com.example.springpictures.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file-save-path}")
    private String fileSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                                       //访问时候需要添加的路径
        registry.addResourceHandler("/images/**")
                                        //文件上传到的路径
                .addResourceLocations("file:"+fileSavePath);
    }

}
