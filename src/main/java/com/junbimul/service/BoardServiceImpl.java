package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.error.exception.BoardApiException;
import com.junbimul.error.model.BoardErrorCode;
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
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .viewCnt(0L)
                .user(userRepository.findById(userDto.getUserId())).build();
        Long boardId = boardRepository.save(board);
        return BoardWriteResponseDto.builder()
                .boardId(boardId)
                .build();
    }

    private static void checkTitleContentLength(BoardRequestDto boardRequestDto) {
        if (boardRequestDto.getTitle().length() > 30) {
            throw new BoardApiException(BoardErrorCode.TITLE_LENGTH_OVER);
        }
        if (boardRequestDto.getContent().length() > 200) {
            throw new BoardApiException(BoardErrorCode.TITLE_LENGTH_OVER);
        }
    }

    // 게시글 전체 가져오기
    public BoardListResponseDto findBoards() {
        List<BoardResponseDto> boardList = boardRepository.findAll()
                .stream()
                .filter(board -> board.getDeletedAt() == null)
                .map(board -> BoardResponseDto.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
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

    // 게시글 하나 가져오기
    public BoardDetailResponseDto getBoardDetailById(Long id) {
        // 요청이 들어올 때 삭제된 건지, 아닌지 판단해야할듯
        Board findBoard = boardRepository.findOne(id);
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        // 이제 여기서 문제가 생김, 예외 처리 해야하는 부분
        findBoard.updateViewCount();
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoard(id).stream().filter(c -> c.getDeletedAt() == null).collect(Collectors.toUnmodifiableList());;
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
                                .updatedAt(comment.getUpdatedAt())
                                .content(comment.getContent())
                                .build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public BoardModifyResponseDto modifyBoard(BoardRequestDto boardRequestDto){
        checkTitleContentLength(boardRequestDto);
        Long boardId = boardRequestDto.getBoardId();
        Board findBoard = boardRepository.findOne(boardId);
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        String modifiedTitle = boardRequestDto.getTitle();
        String modifiedContent = boardRequestDto.getContent();
        findBoard.modify(modifiedTitle, modifiedContent, LocalDateTime.now());

        return BoardModifyResponseDto.builder()
                .boardId(boardId)
                .build();
    }

    public BoardDeleteResponseDto deleteBoard(BoardRequestDto boardRequestDto) {
        Long boardId = boardRequestDto.getBoardId();
        Board findBoard = boardRepository.findOne(boardId);
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        findBoard.delete(LocalDateTime.now());
        for (Comment comment : findBoard.getComments()) {
            comment.deleteComment();
        }

        return BoardDeleteResponseDto.builder()
                .boardId(boardId)
                .build();
    }
}