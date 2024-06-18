package com.kcs.community.service.comment;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.board.CommentInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.Board;
import com.kcs.community.entity.Comment;
import com.kcs.community.entity.User;
import com.kcs.community.repository.board.BoardRepository;
import com.kcs.community.repository.comment.CommentRepository;
import com.kcs.community.repository.user.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public void save(UserInfoDto user, BoardDetails board, String content) {
        Optional<User> findUser = userRepository.findById(user.id());
        if (findUser.isEmpty()) {
            throw new NoSuchElementException("Not exists user");
        }

        Optional<Board> findBoard = boardRepository.findById(board.id());
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException("Not exists board");
        }

        Comment comment = Comment.builder()
                .user(findUser.get())
                .board(findBoard.get())
                .content(content)
                .build();
        commentRepository.save(comment);
    }

    @Override
    public List<CommentInfoDto> findCommentsByBoardId(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            throw new NoSuchElementException("Not exists board");
        }

        List<Comment> findComments = commentRepository.findCommentsByBoardId(boardId);
        return findComments.stream()
                .map(CommentInfoDto::mapToDto)
                .toList();
    }
}
