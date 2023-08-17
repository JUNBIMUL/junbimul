package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.dto.request.BoardModifyRequestDto;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.error.exception.BoardApiException;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.BoardErrorCode;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 게시글 등록
    public BoardWriteResponseDto registerBoard(BoardRequestDto boardRequestDto, UserRequestDto userDto) {
        checkTitleContentLength(boardRequestDto);
        User findUser = userRepository.findById(userDto.getUserId());
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .viewCnt(0L)
                .user(findUser).build();
        Long boardId = boardRepository.save(board);
        return BoardWriteResponseDto.builder()
                .boardId(boardId)
                .build();
    }

    private static void checkTitleContentLength(BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 30) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_OVER);
        }
        if (boardRequestDto.getTitle().length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_ZERO);
        }
        if (boardRequestDto.getContent().length() > 200) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_OVER);
        }
        if (boardRequestDto.getContent().length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_ZERO);
        }
    }

    public BoardDetailResponseDto getBoardDetailById(Long id) {
        Board findBoard = boardRepository.findById(id);
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }
        findBoard.updateViewCount();
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoard(id)
                .stream()
                .filter(c -> c.getDeletedAt() == null)
                .collect(Collectors.toUnmodifiableList());
        return BoardDetailResponseDto.builder()
                .boardId(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .viewCnt(findBoard.getViewCnt())
                .createdAt(findBoard.getCreatedAt())
                .updatedAt(findBoard.getUpdatedAt())
                .nickname(findBoard.getUser().getNickname())
                .commentList(commentsByBoardId.stream()
                        .map(comment -> CommentResponseDto.builder()
                                .commentId(comment.getId())
                                .userName(comment.getUser().getNickname())
                                .createdAt(comment.getCreatedAt())
                                .updatedAt(comment.getUpdatedAt())
                                .content(comment.getContent())
                                .build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public BoardModifyResponseDto modifyBoard(BoardModifyRequestDto boardModifyRequestDto) {

        Board findBoard = boardRepository.findById(boardModifyRequestDto.getBoardId());
        User findUser = userRepository.findById(boardModifyRequestDto.getUserId());
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }

        if (findBoard.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
        if (boardModifyRequestDto.getTitle().length() > 30) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_OVER);
        }
        if (boardModifyRequestDto.getTitle().length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_ZERO);
        }
        if (boardModifyRequestDto.getContent().length() > 200) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_OVER);
        }
        if (boardModifyRequestDto.getContent().length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_ZERO);
        }

        String modifiedTitle = boardModifyRequestDto.getTitle();
        String modifiedContent = boardModifyRequestDto.getContent();
        findBoard.modify(modifiedTitle, modifiedContent, LocalDateTime.now());

        return BoardModifyResponseDto.builder()
                .boardId(findBoard.getId())
                .build();
    }

    public BoardDeleteResponseDto deleteBoard(BoardRequestDto boardRequestDto) {
        Board findBoard = boardRepository.findById(boardRequestDto.getBoardId());
        User findUser = userRepository.findById(boardRequestDto.getUserId());
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }
        if (findBoard.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
        findBoard.delete(LocalDateTime.now());
        for (Comment comment : findBoard.getComments()) {
            if(comment.getDeletedAt() != null) continue;
            comment.deleteComment();
        }

        return BoardDeleteResponseDto.builder()
                .boardId(findBoard.getId())
                .build();
    }

    public BoardListResponseDto findBoards() {
        List<BoardResponseDto> boardList = boardRepository.findAll()
                .stream()
                .filter(board -> board.getDeletedAt() == null)
                .map(board -> BoardResponseDto.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .viewCnt(board.getViewCnt())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .nickname(board.getUser().getNickname())
                        .build())
                .collect(Collectors.toUnmodifiableList());
        return BoardListResponseDto.builder()
                .boardList(boardList)
                .build();
    }
}