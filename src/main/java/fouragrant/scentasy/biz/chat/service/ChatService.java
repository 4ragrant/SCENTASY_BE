package fouragrant.scentasy.biz.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fouragrant.scentasy.biz.chat.domain.Chat;
import fouragrant.scentasy.biz.chat.dto.ChatListResDto;
import fouragrant.scentasy.biz.chat.dto.ChatReqDto;
import fouragrant.scentasy.biz.chat.dto.ChatResDto;
import fouragrant.scentasy.biz.chat.dto.ChatSessionListResDto;
import fouragrant.scentasy.biz.chat.repository.ChatRepository;
import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final String flaskUrl;

    @Autowired
    public ChatService(ChatRepository chatRepository, MemberRepository memberRepository,
                       @Value("${flask.chat.url}") String flaskUrl) {
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
        this.flaskUrl = flaskUrl;
    }

    @Transactional
    public ChatResDto processChat(ChatReqDto chatReqDto, Long memberId, String sessionId) {
        Member member = findMemberById(memberId);
        ExtraInfo extraInfo = member.getExtraInfo();

        if (extraInfo == null) {
            log.warn("ExtraInfo is null for memberId: {}", memberId);
        } else {
            log.info("ExtraInfo for memberId {}: {}", memberId, extraInfo);
        }

        assert extraInfo != null;
        ChatReqDto requestWithExtraInfo = new ChatReqDto(chatReqDto.input(), extraInfo.toDto(), sessionId);
        log.info("{}", requestWithExtraInfo);
        String response = communicateWithFlask(requestWithExtraInfo);

        Chat chat = Chat.builder()
                .input(chatReqDto.input())
                .response(response)
                .member(member)
                .sessionId(sessionId)
                .createdAt(LocalDateTime.now())
                .build();

        chatRepository.save(chat);

        return new ChatResDto(response, sessionId);
    }

    private String communicateWithFlask(ChatReqDto chatReqDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChatReqDto> entity = new HttpEntity<>(chatReqDto, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // JSON 파싱하여 "response" 값만 추출
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode responseNode = rootNode.path("response");

                return responseNode.asText();
            } else {
                throw new RuntimeException("Failed to get a valid response from Flask server");
            }
        } catch (Exception e) {
            log.error("Error communicating with Flask server", e);
            throw new RuntimeException("Error communicating with Flask server", e);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }

    @Transactional
    public List<ChatListResDto> getChatsBySessionIdAndMemberId(String sessionId, Long memberId) {
        return chatRepository.findChatsBySessionIdAndMemberId(sessionId, memberId).stream()
                .map(chat -> ChatListResDto.builder()
                        .chatId(chat.getId())
                        .createdAt(chat.getCreatedAt())
                        .input(chat.getInput())
                        .response(chat.getResponse())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ChatSessionListResDto> getChatSessionsByMemberId(Long memberId) {
        return chatRepository.findDistinctSessionIdsByMemberId(memberId).stream()
                .map(sessionId -> ChatSessionListResDto.builder()
                        .sessionId(sessionId)
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public String generateNewChatSessionId(Long memberId) {
        List<Chat> existingChats = chatRepository.findBySessionIdStartingWith("chat");

        long nextSessionNumber = existingChats.stream()
                .map(Chat::getSessionId)
                .filter(sessionId -> sessionId.startsWith("chat"))
                .mapToInt(sessionId -> {
                    String numberPart = sessionId.replace("chat", "");
                    try {
                        return Integer.parseInt(numberPart);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return "session" + (nextSessionNumber + 1);
    }
}
