package fouragrant.scentasy.biz.post.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PostReqDto {
    private String title;
    private String content;

}
