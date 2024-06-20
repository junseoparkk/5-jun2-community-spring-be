package com.kcs.community.repository.board;

import com.kcs.community.entity.Board;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCustomRepository {
    List<Board> findByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
