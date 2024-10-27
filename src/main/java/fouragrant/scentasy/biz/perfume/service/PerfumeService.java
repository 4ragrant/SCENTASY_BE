package fouragrant.scentasy.biz.perfume.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static fouragrant.scentasy.biz.member.domain.Scent.SCENT_MAPPING;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfumeService {
    private final PerfumeRepository perfumeRepository;
    private final MemberService memberService;

    @Value("${flask.chat.url}") // flask.url 프로퍼티를 주입
    private String flaskUrl; // 필드 추가

    // 향수 ID로 향수 상세 정보 조회
    public Perfume findPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new CommonException(ErrorCode.PERFUME_NOT_FOUND));
    }

    // 멤버 향수 전체 목록 조회
    public List<Perfume> getMemberPerfumes(Long memberId) {
        List<Perfume> perfumes = perfumeRepository.findByMemberId(memberId);

        if (perfumes.isEmpty()) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        return perfumes;
    }

    // 멤버 향수 전체 개수 조회
    public int getMemberPerfumeCount(Long memberId) {
        return perfumeRepository.countByMemberId(memberId);
    }

    public List<String> notesToString() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 헤더만 설정
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // JSON 파싱하여 "response" 값만 추출
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode responseNode = rootNode.path("data");

                // 0과 1의 리스트를 가져옴
                List<Integer> notesList = new ArrayList<>();
                for (JsonNode node : responseNode) {
                    notesList.add(node.asInt());
                }

                // 향수 노트와 매칭하여 선택된 향수 노트의 문자열 리스트 생성
                List<String> selectedScents = new ArrayList<>();
                for (int i = 0; i < notesList.size(); i++) {
                    if (notesList.get(i) == 1) {
                        // 매핑 리스트를 사용하여 Scent 이름을 가져옴
                        selectedScents.add(SCENT_MAPPING.get(i).getDescription());
                    }
                }
                log.info("Selected perfume notes: {}", selectedScents); // 선택된 향수 노트 로그 출력
                return selectedScents; // 향수 노트 문자열 리스트 반환
            } else {
                throw new RuntimeException("Failed to get a valid response from Flask server");
            }
        } catch (Exception e) {
            log.error("Error communicating with Flask server", e);
            throw new RuntimeException("Error communicating with Flask server", e);
        }
    }
}
