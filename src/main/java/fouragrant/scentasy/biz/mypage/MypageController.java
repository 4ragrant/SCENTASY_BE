package fouragrant.scentasy.biz.mypage;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.member.service.ExtraInfoService;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MyPage", description = "My Page 관련 API")
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
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable", required = true, example = "1")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMemberInfo (@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        ExtraInfo extraInfo = extraInfoService.findByMemberId(memberId);
        ExtraInfoResDto responseDto = ExtraInfoResDto.of(member, extraInfo);

        return ResponseEntity.ok(Response.createSuccess("0000", responseDto));
    }

    @Operation(
            summary = "추가정보 수정",
            description = "멤버의 추가정보를 수정하는 메소드"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "Extra-info updated successfully!")
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID, path variable", required = true, example = "1")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "추가정보 수정 요청 본문",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExtraInfoReqDto.class)
            )
    )
    @PatchMapping("/extra-info/{memberId}")
    public ResponseEntity<?> updateExtraInfo(@PathVariable Long memberId, @Validated @RequestBody ExtraInfoReqDto extraInfoReqDto) {
        Member member = memberService.findById(memberId);
        ExtraInfo updatedExtraInfo = extraInfoService.updateExtraInfo(memberId, extraInfoReqDto, member);

        return ResponseEntity.ok(Response.createSuccess("0000", ExtraInfoResDto.of(member, updatedExtraInfo)));
    }
}
