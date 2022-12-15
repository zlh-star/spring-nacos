package com.example.springvalidatecode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
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

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }

//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry){
//
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//
//
//    }

    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Bean
    public Docket SwaggerSpringMvcPligin(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.springvalidatecode"))
                .paths(PathSelectors.any())
                .build();

    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger2,演示接口")
                .description("尚德敏学，唯实唯新")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("zlh", "https://www.cnblogs.com/zlh13437/","zhaolinhai@outlook.com"))
                .version("1.1")
                .build();
    }



}
