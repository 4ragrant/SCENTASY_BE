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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auth", description = "Auth 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ExtraInfoController {
    private final ExtraInfoService extraInfoService;
    private final MemberService memberService;
    private final AuthService authService;

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복확인을 위한 메소드")
    @GetMapping("/exists/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname)
    {
        Boolean isExists = extraInfoService.checkNickname(nickname);
        return ResponseEntity.ok(Response.createSuccess("0000", isExists));
    }

    @Operation(summary = "추가 정보 저장", description = "추가정보 저장을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = ExtraInfoResDto.class)))

    @PostMapping("/extra-info")
    public ResponseEntity<?> createExtraInfo(@Validated @RequestBody ExtraInfoReqDto extraInfoReqDto, @RequestParam("email") String email) {
        Member member = memberService.findByEmail(email);

        if(member == null) {
            return ResponseEntity.badRequest().body(new ExtraInfoResDto<>("Member not found", null));
        }

        // 추가정보 저장
        ExtraInfo createdExtraInfo = extraInfoService.saveExtraInfo(extraInfoReqDto, member);

        // 상태 PENDING -> ACTIVE 변경
        member.setStatus(MemberStatus.ACTIVE);
        authService.activateAccount(member);

        ExtraInfoResDto response = new ExtraInfoResDto(null, List.of(createdExtraInfo));

        return ResponseEntity.ok(Response.createSuccess("0000", response));
    }
}
