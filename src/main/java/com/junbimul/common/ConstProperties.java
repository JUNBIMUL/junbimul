package com.junbimul.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "const")
public class ConstProperties {
    private final int userLoginidLength;
    private final int userPasswordLength;
    private final int userNicknameLength;
    private final int boardTitleLength;
    private final int boardContentLength;
    private final int commentContentLength;
}