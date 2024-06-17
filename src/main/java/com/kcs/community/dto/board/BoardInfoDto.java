package com.kcs.community.dto.board;

import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.Board;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BoardInfoDto(
        Long id,
        String title,
        String content,
        String imageUrl,
        Long likeCount,
        Long viewCount,
        Long commentCount,
        UserInfoDto user,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{
    public static BoardInfoDto mapToDto(Board board) {
        UserInfoDto user = UserInfoDto.mapToDto(board.getUser());
        return BoardInfoDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .imageUrl(board.getImageUrl())
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .commentCount(board.getCommentCount())
                .user(user)
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
