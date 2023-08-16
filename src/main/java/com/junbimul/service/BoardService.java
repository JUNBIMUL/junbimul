package com.junbimul.service;

import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.*;

public interface BoardService {
    public BoardWriteResponseDto registerBoard(BoardRequestDto boardRequestDto, UserRequestDto userDto);

    public BoardListResponseDto findBoards();

    public BoardDetailResponseDto getBoardDetailById(Long id);

    public BoardModifyResponseDto modifyBoard(BoardRequestDto boardRequestDto);

    public BoardDeleteResponseDto deleteBoard(BoardRequestDto boardRequestDto);

}
