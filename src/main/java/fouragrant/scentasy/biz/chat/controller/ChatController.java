package fouragrant.scentasy.biz.chat.controller;

import fouragrant.scentasy.biz.chat.domain.Chat;
import fouragrant.scentasy.biz.chat.dto.ChatListResDto;
import fouragrant.scentasy.biz.chat.dto.ChatReqDto;
import fouragrant.scentasy.biz.chat.dto.ChatResDto;
import fouragrant.scentasy.biz.chat.service.ChatService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chat", description = "Chat 관련 api")
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "채팅", description = "채팅을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "chat successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChatResDto.class)
            )
    )
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable")
    })
    @PostMapping("/{memberId}")
    public ResponseEntity<?> chat(@RequestBody ChatReqDto chatReqDto,
                                  @PathVariable Long memberId) {
            ChatResDto chatResDto = chatService.processChat(chatReqDto, memberId);
            return ResponseEntity.ok(Response.createSuccess("0000", chatResDto));
    }

    @Operation(summary = "멤버별 채팅 날짜 리스트 조회", description = "멤버별 채팅 내역이 있는 날짜 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "Successful retrieve chat dates",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable")
    })
    @GetMapping("/dates/{memberId}")
    public ResponseEntity<Response<List<Date>>> getChatDatesByMemberId(@PathVariable Long memberId) {
        List<Date> chatDates = chatService.getChatDatesByMemberId(memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", chatDates));
    }

    @Operation(summary = "멤버별 날짜별 채팅 내역 조회", description = "멤버별 날짜별 채팅 내역 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "Successful retrieve chat lists",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChatListResDto.class)
            )
    )
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable"),
            @Parameter(name = "date", description = "날,, path variable")
    })
    @GetMapping("/{memberId}/{date}")
    public ResponseEntity<Response<List<ChatListResDto>>> getChatsByMemberIdAndDate(@PathVariable Long memberId,
                                                                                    @PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<ChatListResDto> chats = chatService.getChatsByMemberIdAndDate(memberId, parsedDate);
        return ResponseEntity.ok(Response.createSuccess("0000", chats));
    }



}
