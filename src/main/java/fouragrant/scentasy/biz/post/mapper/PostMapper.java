package fouragrant.scentasy.biz.post.mapper;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResDto toPostResDto(Post post){
        Member member = post.getMember();
        ExtraInfo extraInfo = member.getExtraInfo();
        return PostResDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(extraInfo.getNickname())
                .imageUrl(member.getImageUrl())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .commentCount(post.getCommentCount())
                .build();
    }
}
