package fouragrant.scentasy.biz.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String nickname;
    private String imageUrl;
    private String content;
}
