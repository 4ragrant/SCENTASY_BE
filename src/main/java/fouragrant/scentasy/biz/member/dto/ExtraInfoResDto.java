package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExtraInfoResDto<T> {
    private String nickname;
    private String memberImageUrl;

    public static ExtraInfoResDto of(ExtraInfo extraInfo) {
        ExtraInfoResDto extraInfoResDto = new ExtraInfoResDto();
        extraInfoResDto.nickname = extraInfo.getNickname();

        if (extraInfo.getMember() != null) {
            extraInfoResDto.memberImageUrl = extraInfo.getMember().getImageUrl();
        }

        return extraInfoResDto;
    }
}
