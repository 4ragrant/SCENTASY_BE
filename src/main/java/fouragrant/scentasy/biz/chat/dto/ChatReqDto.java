package fouragrant.scentasy.biz.chat.dto;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import lombok.Builder;
import lombok.Data;


@Builder
public record ChatReqDto(
        String input,
        ExtraInfo extraInfo
) {

}
