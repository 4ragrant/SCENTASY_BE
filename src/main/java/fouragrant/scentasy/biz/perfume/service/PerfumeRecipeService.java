package fouragrant.scentasy.biz.perfume.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.domain.Scent;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.domain.Accord;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.dto.FlaskResponse;
import fouragrant.scentasy.biz.perfume.dto.PerfumeRecipeReqDto;
import fouragrant.scentasy.biz.perfume.dto.PerfumeRecipeResDto;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfumeRecipeService {
    private final PerfumeRepository perfumeRepository;
    private final MemberService memberService;
    @Value("${flask.recipe.url}")
    private String flaskUrl;

    @Transactional
    public PerfumeRecipeResDto processRecipe(Long memberId, String sessionId) {
        Member member = memberService.findById(memberId);
        if (sessionId == null) {
            throw new CommonException(ErrorCode.MISSING_PARAMETER);
        }

        // Flask와 통신하여 JSON 응답을 받아옴
        FlaskResponse responseData = communicateWithFlask(member, sessionId);
        String title = responseData.getTitle();
        String description = responseData.getDescription();
        String recipeArray = responseData.getPredictedNotes();
        List<Accord> accords = responseData.getPredictedAccords();

        List<Scent> notes = mapRecipeArrayToNotes(recipeArray);

        Perfume perfume = Perfume.builder()
                .recipeArray(recipeArray)
                .member(member)
                .title(title)
                .description(description)
                .accords(accords)
                .notes(notes)
                .createdAt(LocalDateTime.now())
                .build();

        perfumeRepository.save(perfume);

        // notes 리스트를 description 값으로 변환
        List<String> noteDescriptions = notes.stream()
                .map(Scent::getDescription)
                .toList();

        return new PerfumeRecipeResDto(perfume.getPerfumeId(), perfume.getCreatedAt(), title, description, noteDescriptions, accords);
    }

    private FlaskResponse communicateWithFlask(Member member, String sessionId) {
        ExtraInfo extraInfo = member.getExtraInfo();
        ExtraInfoReqDto extraInfoReqDto = extraInfo.toDto();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 생성
        PerfumeRecipeReqDto perfumeRecipeReqDto = new PerfumeRecipeReqDto(sessionId, extraInfoReqDto);
        HttpEntity<PerfumeRecipeReqDto> entity = new HttpEntity<>(perfumeRecipeReqDto, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);

                // JSON 데이터를 파싱하여 FlaskResponse 생성
                String title = rootNode.path("title").asText();
                String description = rootNode.path("description").asText();
                String notes = rootNode.path("predicted_notes").asText();
                List<Accord> accords = new ArrayList<>();

                for (JsonNode accordNode : rootNode.path("predicted_accords")) {
                    String accordName = accordNode.path("accord").asText();
                    double value = accordNode.path("value").asDouble();
                    accords.add(new Accord(accordName, value));
                }

                return new FlaskResponse(title, description, notes, accords);
            } else {
                throw new RuntimeException("Failed to get a valid response from Flask server");
            }
        } catch (Exception e) {
            log.error("Error communicating with Flask server", e);
            throw new RuntimeException("Error communicating with Flask server", e);
        }
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
