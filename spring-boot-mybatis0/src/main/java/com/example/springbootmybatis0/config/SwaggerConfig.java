package com.example.springbootmybatis0.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
//    @Value("${swagger.show}")
//    private boolean swaggerShow;

   @Override
   protected void addResourceHandlers(ResourceHandlerRegistry registry) {
       //加载接口内容
       registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

       //加载swagger的页面样式
       registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

       registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").addResourceLocations("classpath:/META-INF/resources/");

   }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.example.springbootmybatis0"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring boot当中集成的title")
                .description("尚德敏学，唯实唯新")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("zlh","https://www.cnblogs.com/zlh13437/","zhaolinhai@outlook.com"))
                .version("1.0")
                .build();
    }
}
