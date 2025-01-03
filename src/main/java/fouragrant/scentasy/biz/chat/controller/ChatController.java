package fouragrant.scentasy.biz.chat.controller;

import fouragrant.scentasy.biz.chat.dto.ChatListResDto;
import fouragrant.scentasy.biz.chat.dto.ChatReqDto;
import fouragrant.scentasy.biz.chat.dto.ChatResDto;
import fouragrant.scentasy.biz.chat.dto.ChatSessionListResDto;
import fouragrant.scentasy.biz.chat.service.ChatService;
import fouragrant.scentasy.biz.member.CustomUserDetails;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/{sessionId}")
    public ResponseEntity<?> chat(@RequestBody ChatReqDto chatReqDto,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable String sessionId) {
            Long memberId = userDetails.getMemberId();
            ChatResDto chatResDto = chatService.processChat(chatReqDto, memberId, sessionId);
            return ResponseEntity.ok(Response.createSuccess("0000", chatResDto));
    }

    @Operation(summary = "새로운 채팅 생성", description = "새로운 채팅 세션을 생성하는 메소드")
    @ApiResponse(responseCode = "0000", description = "Successful new chat session creation",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @PostMapping("/new-session")
    public ResponseEntity<Response<String>> createNewChatSession(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String sessionId = chatService.generateNewChatSessionId(userDetails.getMemberId());
        return ResponseEntity.ok(Response.createSuccess("0000", sessionId));
    }

    @Operation(summary = "세션 ID로 채팅 기록 조회", description = "세션 ID를 통해 해당 채팅의 전체 기록을 조회하는 메소드")
    @ApiResponse(responseCode = "0000", description = "Successful retrieve chat history by session ID",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChatListResDto.class)
            )
    )
    @Parameters({
            @Parameter(name = "sessionId", description = "세션 ID, path variable")
    })
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<Response<List<ChatListResDto>>> getChatsBySessionId(
            @PathVariable String sessionId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();

        // 해당 멤버 ID와 세션 ID에 대한 채팅 기록을 조회
        List<ChatListResDto> chats = chatService.getChatsBySessionIdAndMemberId(sessionId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", chats));
    }

    @Operation(summary = "멤버의 채팅 세션 ID 조회", description = "멤버의 채팅 기록이 있는 모든 세션 ID를 조회하는 메소드")
    @ApiResponse(responseCode = "0000", description = "Successful retrieve chat sessions by member ID",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChatSessionListResDto.class)
            )
    )
    @GetMapping("/sessions")
    public ResponseEntity<Response<List<ChatSessionListResDto>>> getChatSessionsByMemberId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();

        List<ChatSessionListResDto> sessions = chatService.getChatSessionsByMemberId(memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", sessions));
    }

}
