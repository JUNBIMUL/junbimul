package com.junbimul.service;

import com.junbimul.common.ConstProperties;
import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.dto.request.BoardDeleteRequestDto;
import com.junbimul.dto.request.BoardModifyRequestDto;
import com.junbimul.dto.request.BoardWriteRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.error.exception.BoardApiException;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.BoardErrorCode;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableConfigurationProperties(ConstProperties.class)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ConstProperties constProperties;

    // 게시글 등록
    public BoardWriteResponseDto registerBoard(BoardWriteRequestDto boardWriteRequestDto, String loginId) {
        checkTitleContentLength(boardWriteRequestDto.getTitle(), boardWriteRequestDto.getContent());
        User findUser = findUserByLoginId(loginId);
        Board board = Board.builder()
                .title(boardWriteRequestDto.getTitle())
                .content(boardWriteRequestDto.getContent())
                .viewCnt(0L)
                .user(findUser).build();
        Long boardId = boardRepository.save(board);
        return BoardWriteResponseDto.builder()
                .boardId(boardId)
                .build();
    }

    private User findUserByLoginId(String loginId) {
        List<User> findUserList = userRepository.findByLoginId(loginId);
        if (findUserList.size() != 1) throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        User findUser = findUserList.get(0);
        userNullCheck(findUser);
        return findUser;
    }


    public BoardDetailResponseDto getBoardDetailById(Long id) {
        Board findBoard = boardRepository.findById(id);
        boardNullAndDeletedCheck(findBoard);
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


    public BoardModifyResponseDto modifyBoard(BoardModifyRequestDto boardModifyRequestDto, String loginId) {
        Board findBoard = boardRepository.findById(boardModifyRequestDto.getBoardId());
        User findUser = findUserByLoginId(loginId);
        userBoardNullCheckAndHasSameId(findBoard, findUser);
        checkTitleContentLength(boardModifyRequestDto.getTitle(), boardModifyRequestDto.getContent());
        findBoard.modify(boardModifyRequestDto.getTitle(), boardModifyRequestDto.getContent(), LocalDateTime.now());

        return BoardModifyResponseDto.builder()
                .boardId(findBoard.getId())
                .build();
    }


    public BoardDeleteResponseDto deleteBoard(BoardDeleteRequestDto boardDeleteRequestDto, String loginId) {
        Board findBoard = boardRepository.findById(boardDeleteRequestDto.getBoardId());
        List<User> findUserList = userRepository.findByLoginId(loginId);
        if (findUserList.size() != 1) throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        User findUser = findUserList.get(0);
        userBoardNullCheckAndHasSameId(findBoard, findUser);
        findBoard.delete(LocalDateTime.now());
        for (Comment comment : findBoard.getComments()) {
            if (comment.getDeletedAt() != null) continue;
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

    public void userNullCheck(User findUser) {
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
    }

    public void boardNullAndDeletedCheck(Board findBoard) {
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }
    }

    public void userBoardNullCheckAndHasSameId(Board findBoard, User findUser) {
        userNullCheck(findUser);
        boardNullAndDeletedCheck(findBoard);

        if (findBoard.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
    }

    public void checkTitleContentLength(String title, String content) {
        if (title.length() > constProperties.getBoardTitleLength()) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_OVER);
        }
        if (title.length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_ZERO);
        }
        if (content.length() > constProperties.getBoardContentLength()) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_OVER);
        }
        if (content.length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_ZERO);
        }
    }

}