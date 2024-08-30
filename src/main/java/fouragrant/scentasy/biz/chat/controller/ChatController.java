package fouragrant.scentasy.biz.chat.controller;

import fouragrant.scentasy.biz.chat.dto.ChatReqDto;
import fouragrant.scentasy.biz.chat.dto.ChatResDto;
import fouragrant.scentasy.biz.chat.service.ChatService;
import fouragrant.scentasy.common.Response;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{memberId}")
    public ResponseEntity<?> chat(@RequestBody ChatReqDto chatReqDto,
                                  @PathVariable Long memberId) {
            ChatResDto chatResDto = chatService.processChat(chatReqDto, memberId);
            return ResponseEntity.ok(Response.createSuccess("0000", chatResDto));
    }

    @GetMapping("/dates/{memberId}")
    public ResponseEntity<Response<List<Date>>> getChatDatesByMemberId(@PathVariable Long memberId) {
        List<Date> chatDates = chatService.getChatDatesByMemberId(memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", chatDates));
    }

}
