package com.kcs.community.dto.user;

import org.springframework.web.multipart.MultipartFile;

public record SignupRequest(String email, String password, String nickname, MultipartFile profileImg) {
}
