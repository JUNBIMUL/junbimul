package com.junbimul.dto;

import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import lombok.Getter;

@Getter
public class CombinedDto {
    BoardRequestDto boardRequestDto;
    UserRequestDto userRequestDto;
}

