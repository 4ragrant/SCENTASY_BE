package fouragrant.scentasy.biz.mypage;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.member.service.ExtraInfoService;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mypage")
public class MypageController {
    private final ExtraInfoService extraInfoService;
    private final MemberService memberService;

    @Operation(
            summary = "멤버 정보 조회",
            description = "멤버의 정보를 조회하는 메소드"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "Member retrieved successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExtraInfoResDto.class)
            )
    )
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMemberInfo (@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        ExtraInfo extraInfo = extraInfoService.findByMemberId(memberId);
        ExtraInfoResDto responseDto = ExtraInfoResDto.of(member, extraInfo);

        return ResponseEntity.ok(Response.createSuccess("0000", responseDto));
    }

}
