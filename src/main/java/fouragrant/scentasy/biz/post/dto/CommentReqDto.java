package fouragrant.scentasy.biz.post.dto;

import fouragrant.scentasy.biz.post.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDto {
    private String content;
}
