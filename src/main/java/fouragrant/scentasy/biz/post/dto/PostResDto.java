package fouragrant.scentasy.biz.post.dto;


import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResDto {
    private String title;
    private String content;

    private String nickname;
    private String imageUrl; //user 프로필

    private int likeCount;
    private int viewCount;
    private int commentCount;


}
