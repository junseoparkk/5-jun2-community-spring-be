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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public void update(UserInfoDto userDto, Long boardId, Long commentId, String updatedContent) {
        Optional<Comment> findComment = commentRepository.findByBoardIdAndCommentId(boardId, commentId);
        if (findComment.isEmpty()) {
            throw new NoSuchElementException("Not exists comment");
        }

        Comment comment = findComment.get();

        if (!comment.getUser().getId().equals(userDto.id())) {
            throw new IllegalStateException("Not Valid User");
        }

        comment.update(updatedContent);
        commentRepository.save(comment);
    }
}
