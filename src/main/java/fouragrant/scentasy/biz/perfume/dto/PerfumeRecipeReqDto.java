package fouragrant.scentasy.biz.perfume.dto;

import lombok.Builder;

@Builder
public record PerfumeRecipeReqDto(
        String sessionId
) {
}
