package com.junbimul.service;

import com.junbimul.dto.request.CommentDeleteRequestDto;
import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.dto.response.CommentDeleteResponseDto;
import com.junbimul.dto.response.CommentModifyResponseDto;
import com.junbimul.dto.response.CommentWriteResponseDto;

public interface CommentService {
    public CommentWriteResponseDto registComment(CommentRequestDto commentRequestDto, String loginId);

    public CommentModifyResponseDto modifyComment(CommentModifyRequestDto commentModifyRequestDto, String loginId);

    public CommentDeleteResponseDto deleteComment(CommentDeleteRequestDto commentDeleteRequestDto, String loginId);
}
