package com.junbimul.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/reissue")
                .excludePathPatterns("/signup")
                .excludePathPatterns("swagger-resources/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/webjars/**/")
                .excludePathPatterns("/v3/api-docs/**/");

    }
}
