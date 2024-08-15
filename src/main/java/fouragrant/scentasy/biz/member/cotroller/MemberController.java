package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.dto.MemberDtoReq;
import fouragrant.scentasy.biz.member.service.member.MemberService;
import fouragrant.scentasy.biz.member.service.member.TokenService;
import fouragrant.scentasy.common.controller.BaseApiController;
import fouragrant.scentasy.common.controller.BaseApiDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Tag(name="Member", description = "Member API")
public class MemberController extends BaseApiController<BaseApiDto<?>>{
    private final MemberService memberService;
    private final TokenService tokenService;

    @Operation(summary = "이메일 중복 체크")
    @GetMapping("/email-check/{email}")
    public ResponseEntity<BaseApiDto<?>> emailCheck(@PathVariable String email) throws Exception {
        try {
            memberService.emailCheck(email);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "이메일 중복 체크 실패 : " + e.getMessage());
        }
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ApiResponse(responseCode = "0000", description = "가입 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "test@naver.com"),
            @Parameter(name = "password", description = "패스워드", example = "1234"),
            @Parameter(name = "nickname", description = "닉네임", example = "scentasy"),
    })
    public ResponseEntity<BaseApiDto<?>> signup(@RequestBody MemberDtoReq.SignUpDto signUpDto) throws Exception {
        try {
            memberService.signUp(signUpDto);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "회원가입 실패 : " + e.getMessage());
        }
    }

    @Operation(summary = "추가정보입력", description = "추가정보입력")
    @PostMapping("/extrainfo")
    @Parameters({
            @Parameter(name = "gender", description = "선호하는 향수 성별", example = "여성"),
            @Parameter(name = "age", description = "선호하는 연령", example = "10대"),
            @Parameter(name = "season", description = "선호하는 계절", example = "봄")
    })
    public ResponseEntity<BaseApiDto<?>> extrainfo(@RequestBody MemberDtoReq.ExtraInfoDto extraInfoDto) throws Exception {
        try {
            memberService.extraInfo(extraInfoDto);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "추가정보 입력 실패 : " + e.getMessage());
        }
    }


//    @Operation(summary = "로그인", description = "통힙로그인")
//    @PostMapping("login")


//    @Operation(summary = "토큰 리프레시", description = "리프레시 토큰으로 억세스 토큰 재발급")
//    @PostMapping("/reissue")


//    @Operation(summary = "로그아웃")
//    @PostMapping("logout")



}
