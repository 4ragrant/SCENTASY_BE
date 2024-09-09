package fouragrant.scentasy.biz.post.dto;


import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResDto {
    private PerfumeDto perfume;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private String nickname;
    private String imageUrl; //user 프로필

    private int likeCount;
    private int viewCount;
    private int commentCount;
}
