package com.kcs.community.dto.board;

import com.kcs.community.dto.user.UserInfoDto;
import com.kcs.community.entity.Board;
import com.kcs.community.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record BoardDetails(
        Long id,
        String title,
        String content,
        String imageUrl,
        Long likeCount,
        Long viewCount,
        Long commentCount,
        UserInfoDto user,
        List<CommentInfoDto> comments,
        LocalDateTime createdAt
) {
    public static BoardDetails mapToDto(Board board) {
        UserInfoDto user = UserInfoDto.mapToDto(board.getUser());
        List<Comment> comments = board.getComments();
        List<CommentInfoDto> commentInfoDtos = comments.stream()
                .map(CommentInfoDto::mapToDto)
                .toList();
        return BoardDetails.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .imageUrl(board.getImageUrl())
                .likeCount(board.getLikeCount())
                .viewCount(board.getViewCount())
                .commentCount(board.getCommentCount())
                .user(user)
                .comments(commentInfoDtos)
                .createdAt(board.getCreatedAt())
                .build();
    }
}
