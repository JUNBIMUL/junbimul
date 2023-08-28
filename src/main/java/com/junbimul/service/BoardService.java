package com.junbimul.service;

import com.junbimul.dto.request.BoardDeleteRequestDto;
import com.junbimul.dto.request.BoardModifyRequestDto;
import com.junbimul.dto.request.BoardWriteRequestDto;
import com.junbimul.dto.response.*;

public interface BoardService {
    public BoardWriteResponseDto registerBoard(BoardWriteRequestDto boardRequestDto, String loginId);

    public BoardListResponseDto findBoards();

    public BoardDetailResponseDto getBoardDetailById(Long id);

    public BoardModifyResponseDto modifyBoard(BoardModifyRequestDto boardModifyRequestDto, String loginId);

    public BoardDeleteResponseDto deleteBoard(BoardDeleteRequestDto boardDeleteRequestDto, String loginId);

}
