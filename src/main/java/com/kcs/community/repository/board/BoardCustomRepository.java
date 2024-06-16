package com.kcs.community.repository;

import com.kcs.community.entity.Board;

public interface BoardCustomRepository {
    Long saveBoard(Board board);
}
