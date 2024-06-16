package com.kcs.community.repository;

import com.kcs.community.entity.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long saveBoard(Board board) {
        jpaQueryFactory.
    }
}
