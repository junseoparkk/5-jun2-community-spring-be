package com.kcs.community.repository.comment;

import static com.kcs.community.entity.QComment.comment;

import com.kcs.community.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findCommentsByBoardId(Long boardId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .where(comment.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        jpaQueryFactory
                .delete(comment)
                .where(comment.user.id.eq(userId))
                .execute();
    }

    @Override
    public Optional<Comment> findByBoardIdAndCommentId(Long boardId, Long commentId) {
        return jpaQueryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardId).and(comment.id.eq(commentId)))
                .fetch()
                .stream()
                .findFirst();
    }
}
