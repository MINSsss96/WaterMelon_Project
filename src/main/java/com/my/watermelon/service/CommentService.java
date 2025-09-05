import com.my.watermelon.dto.CommentDto;
import com.my.watermelon.entity.CommentEntity;
import com.my.watermelon.entity.User;
import com.my.watermelon.repository.CommentRepository;
import com.my.watermelon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    public CommentDto createComment(Long songId, Long userId, String content, Long parentId) {
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("곡을 찾을 수 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없음"));

        CommentEntity comment = CommentEntity.builder()
                .song(song)
                .user(user)
                .content(content)
                .build();

        if (parentId != null) {
            CommentEntity parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글 없음"));
            comment.setParent(parent);
        }

        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    // 곡별 댓글 조회 (대댓글 포함)
    public List<CommentDto> getCommentsBySong(Long songId) {
        return commentRepository.findBySongIdAndParentIsNullOrderByCreatedAtDesc(songId)
                .stream()
                .map(CommentDto::fromEntity)
                .toList();
    }

    // 댓글 수정
    public CommentDto updateComment(Long commentId, Long userId, String newContent) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new SecurityException("본인 댓글만 수정 가능");
        }

        comment.setContent(newContent);
        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, Long userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new SecurityException("본인 댓글만 삭제 가능");
        }

        commentRepository.delete(comment);
    }
}
