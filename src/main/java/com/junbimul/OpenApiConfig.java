package com.junbimul;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("준비물 API 문서")
                .description("준비물 프로젝트 관련 사용법입니다");

        return new OpenAPI()
                .info(info);
    }
}
