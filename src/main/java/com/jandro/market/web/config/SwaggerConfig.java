package com.jandro.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jandro.market.web.controller"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Market REST API")
                .description("At the moment of its conception, this project is for learning purposes. "+
                        "Is a REST API created with Spring Boot. Implement authentication with JSON Web Token.")
                .license("Apache 2.0").version("1.0.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
}
