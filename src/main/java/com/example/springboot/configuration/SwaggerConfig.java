package com.example.springboot.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return regex("/users/*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("User API")
                .description("AWS Image Download/Upload API reference for developers")
                .termsOfServiceUrl("http://github.com/osakturk")
                .contact(new Contact("Omer", "Akturk","omerakturk@outlook.com.tr")).license("Osakturk License")
                .licenseUrl("omerakturk@outlook.com.tr").version("1.0").build();
    }

}