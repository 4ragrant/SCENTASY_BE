package fouragrant.scentasy.biz.post.dto;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    private String title;
    private String content;
}
