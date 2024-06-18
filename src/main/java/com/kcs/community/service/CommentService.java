package com.kcs.community.service;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.board.CommentInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import java.util.List;


public interface CommentService {
    void save(UserInfoDto user, BoardDetails board, String content);
    List<CommentInfoDto> findCommentsByBoardId(Long boardId);
}
