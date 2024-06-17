package com.kcs.community.dto.board;

import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentInfoDto(Long id, String content, UserInfoDto user, LocalDateTime createdAt) {
    public static CommentInfoDto mapToDto(Comment comment) {
        UserInfoDto user = UserInfoDto.mapToDto(comment.getUser());
        return CommentInfoDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(user)
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
