package fouragrant.scentasy.biz.chat.dto;

import lombok.Builder;

@Builder
public record ChatSessionListResDto(
        String sessionId
) {
}
