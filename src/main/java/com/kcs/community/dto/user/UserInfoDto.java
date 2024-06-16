package com.kcs.community.dto.user;

import lombok.Builder;

@Builder
public record UserInfoDto(String email, String nickname, String profileUrl) {
}
