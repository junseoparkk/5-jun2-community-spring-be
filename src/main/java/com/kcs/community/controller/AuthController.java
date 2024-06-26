package com.kcs.community.controller;

import com.kcs.community.dto.user.SignupRequest;
import com.kcs.community.dto.user.SignupResponse;
import com.kcs.community.service.S3ImageService;
import com.kcs.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final S3ImageService s3ImageService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg
    ) {
        try {
            String imagePath = s3ImageService.upload(profileImg, "profiles");
            SignupRequest request = new SignupRequest(email, password, nickname, imagePath);
            SignupResponse response = userService.signup(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("signup: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
