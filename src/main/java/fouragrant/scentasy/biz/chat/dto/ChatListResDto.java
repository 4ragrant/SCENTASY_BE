package fouragrant.scentasy.biz.chat.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatListResDto(
        Long chatId,
        LocalDateTime createdAt,
        String input,
        String response
) {
}
