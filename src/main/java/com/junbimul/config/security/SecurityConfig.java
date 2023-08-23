package com.junbimul.config.security;

import com.junbimul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private static String secretKey = "kk";

    private static final String[] PUBLIC_API_URI = {
            "/user/login/", "/user/signup"
    };

    @Bean
    @Order(0)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        httpSecurity
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers(matchers -> matchers.antMatchers(PUBLIC_API_URI))
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
                .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
