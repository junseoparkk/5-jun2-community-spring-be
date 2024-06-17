package com.kcs.community.service;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.user.UserInfoDto;


public interface CommentService {
    void save(UserInfoDto user, BoardDetails board, String content);
}
