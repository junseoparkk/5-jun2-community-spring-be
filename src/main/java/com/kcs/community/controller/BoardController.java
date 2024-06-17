package com.kcs.community.controller;

import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.User;
import com.kcs.community.service.BoardService;
import com.kcs.community.service.UserService;
import java.io.File;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    @Value("${spring.servlet.multipart.location}")
    private String imagePath;
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BoardInfoDto> writeBoard(
            @AuthenticationPrincipal UserDetails user,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        User findUser = userService.findByEmail(user.getUsername());

        log.info("user: {}, title: {}, content: {}, imagePath: {}", findUser.getEmail(), title, content, imagePath);

        BoardInfoDto response = boardService.save(findUser, title, content, image, imagePath);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BoardInfoDto>> findAllBoards() {
        List<BoardInfoDto> boards = boardService.findAll();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public String findBoard(@PathVariable Long boardId) {
        return "find board";
    }

    @PostMapping("/{boardId}")
    public String editBoard(@PathVariable Long boardId) {
        return "edit board";
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        return "delete board";
    }
}
