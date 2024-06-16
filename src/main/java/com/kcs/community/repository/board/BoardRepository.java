package com.kcs.community.repository;

import com.kcs.community.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Long, Board>, BoardCustomRepository {
}
