package com.my.watermelon.dto;

import com.my.watermelon.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long userId;
    private String userNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDto> children; // 대댓글 포함

    public static CommentDto fromEntity(CommentEntity entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userNickname(entity.getUser().getNickname())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .children(entity.getChildren().stream()
                        .map(CommentDto::fromEntity)
                        .toList())
                .build();
    }
}
