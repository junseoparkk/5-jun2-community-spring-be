package com.kcs.community.dto.user;

public record SignupRequest(String email, String password, String nickname, String profileUrl) {
}
