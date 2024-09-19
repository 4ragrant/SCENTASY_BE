package fouragrant.scentasy.biz.post.dto;

import fouragrant.scentasy.biz.post.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String nickname;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private CommentResDto parentComment;

    public CommentResDto(Comment comment) {
        this.nickname = comment.getMember().getExtraInfo().getNickname();
        this.imageUrl = comment.getMember().getImageUrl();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updateAt = comment.getUpdatedAt();
        this.parentComment = comment.getParentComment() != null ? new CommentResDto(comment.getParentComment()) : null;
    }
}
