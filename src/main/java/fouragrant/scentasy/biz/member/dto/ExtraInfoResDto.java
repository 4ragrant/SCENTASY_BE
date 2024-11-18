package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.*;
import fouragrant.scentasy.biz.perfume.domain.Scent;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraInfoResDto<T> {
    private String email;
    private String imageUrl;
    private String nickname;
    private Season season;
    private Gender gender;
    private Age age;
    private List<Scent> likedScents;
    private List<Scent> dislikedScents;

    public static ExtraInfoResDto of(Member member, ExtraInfo extraInfo) {
        return ExtraInfoResDto.builder()
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .nickname(extraInfo.getNickname())
                .season(extraInfo.getSeason())
                .gender(extraInfo.getGender())
                .age(extraInfo.getAge())
                .likedScents(extraInfo.getLikedScents())
                .dislikedScents(extraInfo.getDislikedScents())
                .build();
    }
}
