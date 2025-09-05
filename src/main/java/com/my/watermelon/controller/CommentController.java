package com.my.watermelon.controller;


import com.my.watermelon.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final Comments commentService;

    // 댓글 작성
    @PostMapping("/{songId}")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable Long songId,
            @RequestParam Long userId,
            @RequestParam String content,
            @RequestParam(required = false) Long parentId) {
        return ResponseEntity.ok(
                commentService.createComment(songId, userId, content, parentId)
        );
    }

    // 곡별 댓글 조회
    @GetMapping("/{songId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long songId) {
        return ResponseEntity.ok(commentService.getCommentsBySong(songId));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestParam String newContent) {
        return ResponseEntity.ok(
                commentService.updateComment(commentId, userId, newContent)
        );
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

}
