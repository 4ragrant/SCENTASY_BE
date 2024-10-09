package fouragrant.scentasy.biz.perfume.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.domain.Scent;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.dto.PerfumeRecipeReqDto;
import fouragrant.scentasy.biz.perfume.dto.PerfumeRecipeResDto;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.nodes.ScalarNode;

@Service
@Slf4j
public class PerfumeRecipeService {
    private final PerfumeRepository perfumeRepository;
    private final MemberRepository memberRepository;
    private final String flaskUrl;

    @Autowired
    public PerfumeRecipeService(PerfumeRepository perfumeRepository, MemberRepository memberRepository,
                       @Value("${flask.recipe.url}") String flaskUrl) {
        this.perfumeRepository = perfumeRepository;
        this.memberRepository = memberRepository;
        this.flaskUrl = flaskUrl;
    }

    @Transactional
    public PerfumeRecipeResDto processRecipe(Long memberId) {
        Member member = findMemberById(memberId);
        String recipeArray = communicateWithFlask();

        List<Scent> notes = mapRecipeArrayToNotes(recipeArray);

        Perfume perfume = Perfume.builder()
                .recipeArray(recipeArray)
                .member(member)
                .notes(notes)
                .createdAt(LocalDateTime.now())
                .build();

        perfumeRepository.save(perfume);

        return new PerfumeRecipeResDto(notes);
    }

    private String communicateWithFlask() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 생성
        PerfumeRecipeReqDto perfumeRecipeReqDto = new PerfumeRecipeReqDto("default_session");

        HttpEntity<PerfumeRecipeReqDto> entity = new HttpEntity<>(perfumeRecipeReqDto, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                // JSON scentasy파싱하여 "response" 값만 추출
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode responseNode = rootNode.path("predicted_notes");

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

    private List<Scent> mapRecipeArrayToNotes(String recipeArray) {
        List<Scent> notes = new ArrayList<>();
        try {
            // 문자열을 배열로 변환
            String[] recipeArrayValues = recipeArray.split(",\s*");

            // 값이 1인 인덱스 찾아서 매핑
            for (int i = 0; i < recipeArrayValues.length; i++) {
                if ("1".equals(recipeArrayValues[i])) {
                    notes.add(Scent.values()[i]);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing recipeArray", e);
        }

        return notes;
    }

}
