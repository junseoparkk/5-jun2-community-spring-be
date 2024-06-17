package com.kcs.community.dto.user;

import com.kcs.community.entity.RoleType;
import com.kcs.community.entity.User;
import lombok.Builder;

@Builder
public record UserInfoDto(Long id, String email, String nickname, String profileUrl, String roleType) {
    public static UserInfoDto mapToDto(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .roleType(user.getRole().name())
                .build();
    }
}
