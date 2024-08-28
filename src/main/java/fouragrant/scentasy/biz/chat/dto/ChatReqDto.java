package fouragrant.scentasy.biz.chat.dto;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;

public record ChatReqDto(
        String input,
        ExtraInfo extraInfo
) {

}
