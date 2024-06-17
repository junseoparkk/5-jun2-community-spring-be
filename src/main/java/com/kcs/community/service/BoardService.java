package com.kcs.community.service;

import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.entity.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    BoardInfoDto save(User user, String title, String content, MultipartFile image, String imagePath) throws IOException;
}
