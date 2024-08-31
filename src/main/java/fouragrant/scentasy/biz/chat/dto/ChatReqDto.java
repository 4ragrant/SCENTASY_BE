package fouragrant.scentasy.biz.chat.dto;

import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import lombok.Builder;


@Builder
public record ChatReqDto(
        String input,
        ExtraInfoReqDto extraInfo
) {

}
