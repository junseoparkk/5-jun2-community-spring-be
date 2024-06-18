package com.kcs.community.controller;

import com.kcs.community.auth.CustomUserDetails;
import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInfoDto userDto = userService.findByEmail(userDetails.getUsername());
        log.info("findUser email: {}, nickname: {}, profileUrl: {}", userDto.email(), userDto.nickname(), userDto.profileUrl());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/me")
    public ResponseEntity<UserInfoDto> updateUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart(name = "nickname", required = false) String nickname,
            @RequestPart(name = "profileImg", required = false)MultipartFile profileImg
    ) {
        UserInfoDto findUser = userService.findByEmail(userDetails.getUsername());
        if (nickname == null && profileImg.isEmpty()) {
            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }
        UserInfoDto updatedUser = userService.updateInfo(findUser, nickname, profileImg);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/me/password")
    public ResponseEntity<UserInfoDto> updateUserPassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart(name = "password", required = false) String password
    ) {
        UserInfoDto findUser = userService.findByEmail(userDetails.getUsername());
        if (password == null) {
            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }
        UserInfoDto updatedUser = userService.updatePassword(findUser, password);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
