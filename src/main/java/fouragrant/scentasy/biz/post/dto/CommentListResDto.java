package fouragrant.scentasy.biz.post.dto;

import fouragrant.scentasy.biz.post.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResDto {
    private String nickname;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private List<CommentListResDto> childComments = new ArrayList<>(); // 자식 댓글 리스트

    public CommentListResDto(Comment comment, List<CommentListResDto> childComments) {
        this.nickname = comment.getMember().getExtraInfo().getNickname();
        this.imageUrl = comment.getMember().getImageUrl();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updateAt = comment.getUpdatedAt();
        this.childComments = (childComments != null) ? childComments : new ArrayList<>();
    }
}
