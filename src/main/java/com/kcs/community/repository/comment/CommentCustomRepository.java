package com.kcs.community.repository.comment;

import com.kcs.community.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentCustomRepository {
    List<Comment> findCommentsByBoardId(Long boardId);
    void deleteAllByUserId(Long userId);
    Optional<Comment> findByBoardIdAndCommentId(Long boardId, Long commentId);
}
