package com.junbimul.dto;

import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import lombok.Getter;

@Getter
public class CombinedDto {
    BoardRequestDto boardRequestDto;
    UserSignupRequestDto userRequestDto;
}

