package fouragrant.scentasy.biz.perfume.controller;

import fouragrant.scentasy.biz.member.CustomUserDetails;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import fouragrant.scentasy.biz.perfume.dto.PerfumeRecipeResDto;
import fouragrant.scentasy.biz.perfume.service.PerfumeRecipeService;
import fouragrant.scentasy.biz.perfume.service.PerfumeService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Perfume", description = "향수 관련 API")
@RestController
@RequestMapping("/api/perfume")
@RequiredArgsConstructor
public class PerfumeController {
    private final PerfumeService perfumeService;
    private final PerfumeRecipeService perfumeRecipeService;
    private final MemberService memberService;

    @Operation(
            summary = "향수 레시피 생성",
            description = "생성된 향수 레시피 생성을 위한 메소드, 레시피 노트 5개를 반환합니다.")
    @ApiResponse(
            responseCode = "0000",
            description = "Perfume recipeArray saved successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PerfumeRecipeResDto.class)
            )
    )
    @PostMapping("/recipe/{sessionId}")
    public ResponseEntity<?> createRecipe(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable String sessionId) {
        Long memberId = userDetails.getMemberId();
        PerfumeRecipeResDto perfumeRecipeResDto = perfumeRecipeService.processRecipe(memberId, sessionId);
        return ResponseEntity.ok(Response.createSuccess("0000", perfumeRecipeResDto));
    }

    @Operation(
            summary = "향수 상세 정보 조회",
            description = "생성된 향수의 상세 정보 조회를 위한 메소드"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "Perfume details retrieved successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PerfumeDto.class)
            )
    )
    @GetMapping("/{perfumeId}")
    public ResponseEntity<?> getPerfumeDetails(@PathVariable Long perfumeId) {
        Perfume perfume = perfumeService.findPerfumeById(perfumeId);
        PerfumeDto perfumeDto = PerfumeDto.fromEntity(perfume);

        return ResponseEntity.ok(Response.createSuccess("0000", perfumeDto));
    }

    @Operation(
            summary = "전체 향수 목록 조회",
            description = "특정 멤버의 전체 향수 목록을 조회를 위한 메소드"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "Perfume list retrieved successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Perfume.class)
            )
    )
    @GetMapping
    public ResponseEntity<?> getMemberPerfumes(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        List<Perfume> perfumes = perfumeService.getMemberPerfumes(memberId);

        // Perfume 리스트를 PerfumeDto 리스트로 변환
        List<PerfumeDto> perfumeDtos = perfumes.stream()
                .map(PerfumeDto::fromEntity)
                .toList();

        return ResponseEntity.ok(Response.createSuccess("0000", perfumeDtos));
    }

    @Operation(
            summary = "전체 향수 개수 조회",
            description = "특정 멤버의 전체 향수 개수 조회를 위한 메소드"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "Perfume count retrieved successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class)
            )
    )
    @GetMapping("/count")
    public ResponseEntity<?> getMemberPerfumeCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        int perfumeCount = perfumeService.getMemberPerfumeCount(memberId);

        return ResponseEntity.ok(Response.createSuccess("0000", perfumeCount));
    }
}
