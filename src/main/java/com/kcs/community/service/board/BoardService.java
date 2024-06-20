package com.kcs.community.service.board;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    BoardInfoDto save(UserInfoDto user, String title, String content, MultipartFile image) throws IOException;
    List<BoardInfoDto> findAll();
    BoardDetails findById(Long id);
    BoardDetails update(Long id, Long userId, String title, String content, MultipartFile image) throws IOException;
    void delete(Long id, Long userId) throws IOException;
}
