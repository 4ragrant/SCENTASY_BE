package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.domain.MemberStatus;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.member.service.AuthService;
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

@Tag(name = "Auth", description = "Auth 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ExtraInfoController {
    private final ExtraInfoService extraInfoService;
    private final MemberService memberService;
    private final AuthService authService;

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "validate successfully!")
    @Parameters({
            @Parameter(name = "nickname", description = "닉네임, path variable")
    })
    @GetMapping("/exists/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname)
    {
        Boolean isExists = extraInfoService.checkNickname(nickname);
        return ResponseEntity.ok(Response.createSuccess("0000", isExists));
    }

    @Operation(summary = "추가 정보 저장", description = "추가정보 저장을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "add extra-info successfully!",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExtraInfoResDto.class)
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "추가정보 저장 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ExtraInfoReqDto.class)
            )
    )
    @PostMapping("/extra-info")
    public ResponseEntity<?> createExtraInfo(@Validated @RequestBody ExtraInfoReqDto extraInfoReqDto, @RequestParam("email") String email) {
        // 추가정보 저장
        Member member = memberService.findByEmail(email);
        ExtraInfo extraInfo = extraInfoService.saveExtraInfo(extraInfoReqDto, member);

        // 상태 PENDING -> ACTIVE 변경
        member.setStatus(MemberStatus.ACTIVE);
        authService.activateAccount(member);

        return ResponseEntity.ok(Response.createSuccess("0000", ExtraInfoResDto.of(member, extraInfo)));
    }

    @Operation(summary = "추가정보 수정", description = "멤버의 추가정보를 수정하는 메소드")
    @ApiResponse(responseCode = "0000", description = "update extra-info successfully!")
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
