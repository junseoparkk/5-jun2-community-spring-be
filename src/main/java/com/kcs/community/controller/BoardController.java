package com.kcs.community.controller;

import com.kcs.community.dto.board.BoardDetails;
import com.kcs.community.dto.board.BoardInfoDto;
import com.kcs.community.dto.board.CommentInfoDto;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.service.board.BoardService;
import com.kcs.community.service.comment.CommentService;
import com.kcs.community.service.user.UserService;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BoardInfoDto> writeBoard(
            @AuthenticationPrincipal UserDetails user,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        UserInfoDto findUser = userService.findByEmail(user.getUsername());

        log.info("user: {}, title: {}, content: {}", findUser.email(), title, content);

        BoardInfoDto response = boardService.save(findUser, title, content, image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BoardInfoDto>> findAllBoards() {
        List<BoardInfoDto> boards = boardService.findAll();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetails> findBoard(@PathVariable(name = "boardId") Long boardId) {
        try {
            BoardDetails findBoard = boardService.findById(boardId);
            log.info("findBoard: {}", findBoard);
            return new ResponseEntity<>(findBoard, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<BoardDetails> editBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "boardId") Long boardId,
            @RequestPart(name = "title") String title,
            @RequestPart(name = "content") String content,
            @RequestPart(name = "image") MultipartFile image
    ) {
        try {
            UserInfoDto userDto = userService.findByEmail(userDetails.getUsername());
            BoardDetails updatedBoard = boardService.update(boardId, userDto.id(), title, content, image);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        } catch (NoSuchElementException | IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "boardId") Long boardId) {
        try {
            UserInfoDto findUser = userService.findByEmail(userDetails.getUsername());
            boardService.delete(boardId, findUser.id());
            return new ResponseEntity<>("board delete complete", HttpStatus.OK);
        } catch (NoSuchElementException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{boardId}/comments")
    public String writeComment(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable(name = "boardId") Long boardId,
            @RequestParam(name = "content") String content
    ) {
        UserInfoDto findUser = userService.findByEmail(user.getUsername());
        BoardDetails findBoard = boardService.findById(boardId);
        commentService.save(findUser, findBoard, content);
        return "ok";
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentInfoDto>> findAllComments(@PathVariable(name = "boardId") Long boardId) {
        List<CommentInfoDto> comments = commentService.findCommentsByBoardId(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<String> editComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable(name = "boardId") Long boardId,
            @PathVariable(name = "commentId") Long commentId,
            @RequestParam(name = "content") String content
    ) {
        try {
            UserInfoDto findUser = userService.findByEmail(userDetails.getUsername());
            commentService.update(findUser, boardId, commentId, content);
            return new ResponseEntity<>("comment edit complete", HttpStatus.OK);
        } catch (NoSuchElementException | AuthenticationException e) {
            return new ResponseEntity<>("comment edit failed", HttpStatus.BAD_REQUEST);
        }
    }
}
