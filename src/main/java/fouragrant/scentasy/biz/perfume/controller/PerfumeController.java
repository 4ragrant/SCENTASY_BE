package fouragrant.scentasy.biz.perfume.controller;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import fouragrant.scentasy.biz.perfume.service.PerfumeService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Perfume", description = "향수 관련 API")
@RestController
@RequestMapping("/api/perfume")
@RequiredArgsConstructor
public class PerfumeController {
    private final PerfumeService perfumeService;
    private final MemberService memberService;

    // 생성된 향수 저장
    @PostMapping("/{memberId}")
    public ResponseEntity<?> createPerfume(@PathVariable Long memberId, @RequestBody PerfumeDto perfumeDto) {
        Member member = memberService.findById(memberId);
        perfumeService.createPerfume(perfumeDto, member);

        return ResponseEntity.ok(Response.createSuccess("0000", perfumeDto));
    }

    // 향수 목록 조회

    // 전체 향수 목록 조회
}
