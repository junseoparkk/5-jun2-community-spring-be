package com.kcs.community.repository.comment;

import static com.kcs.community.entity.QComment.comment;

import com.kcs.community.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findCommentsByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(comment)
                .where(comment.board.id.eq(boardId))
                .fetch();
    }
}
