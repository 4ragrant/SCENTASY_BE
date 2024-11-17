package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import lombok.Builder;

@Builder
public record PerfumeRecipeReqDto(
        String sessionId,
        ExtraInfoReqDto extraInfo
) {
}
