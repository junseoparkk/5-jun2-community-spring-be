package com.kcs.community.service;

import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.entity.Board;
import com.kcs.community.entity.User;
import com.kcs.community.repository.board.BoardCustomRepository;
import com.kcs.community.repository.board.BoardRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public BoardInfoDto save(User user, String title, String content, MultipartFile image, String imagePath) throws IOException {
        UUID uuid = UUID.randomUUID();
        String originalName = image.getOriginalFilename();
        String imageUrl = "";
        log.info("imagePath: {}", imagePath);

        if (!image.isEmpty()) {
            String fileName = uuid + originalName;
            File saveFile = new File(imagePath, fileName);
            image.transferTo(saveFile);
            imageUrl = imagePath + fileName;
        }

        Board board = Board.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0L)
                .viewCount(0L)
                .commentCount(0L)
                .user(user)
                .build();
        Board savedBoard = boardRepository.save(board);
        return BoardInfoDto.mapToDto(savedBoard);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardInfoDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardInfoDto::mapToDto)
                .toList();
    }
}
