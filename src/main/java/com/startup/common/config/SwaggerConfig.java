package com.startup.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "start-up API",
                description = "start-up 프로젝트 API 문서",
                version = "v1.0.0",
                contact = @Contact(name = "start-up team")
        )
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("start-up API")
                        .description("start-up 프로젝트 API 문서")
                        .version("v1.0.0")
                        .license(new License().name("Internal Use Only")));
    }
}
