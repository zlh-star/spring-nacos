package com.springboot.springbootjpahikari.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.show}")
    private boolean SwaggerShow;

    @Bean
    public Docket SwaggerMvcPlugin(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(SwaggerShow)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("spring-boot-jpa测试")
                .description("尚德敏学，唯实唯新")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("zlh","https://www.cnblogs.com/zlh13437/","zhaolinhai@outlook.com"))
                .version("1.0")
                .build();

    }


}
