package com.kcs.community.service;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.Board;
import com.kcs.community.entity.User;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    BoardInfoDto save(UserInfoDto user, String title, String content, MultipartFile image, String imagePath) throws IOException;
    List<BoardInfoDto> findAll();
    BoardDetails findById(Long id);
}
