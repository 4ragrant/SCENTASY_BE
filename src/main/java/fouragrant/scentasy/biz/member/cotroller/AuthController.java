package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.biz.member.dto.TokenReqDto;
import fouragrant.scentasy.biz.member.service.AuthService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth 관련 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = MemberReqDto.class)))
    @PostMapping("/signup")
    public  ResponseEntity<?> signup(@RequestBody MemberReqDto memberReqDto) {
            MemberResDto memberResDto = authService.signup(memberReqDto);
            return ResponseEntity.ok(Response.createSuccess("0000", memberResDto));
    }

    @Operation(summary = "일반 로그인", description = "일반 로그인을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = MemberReqDto.class)))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto memberDtoRes) {
        TokenDto tokenDto = authService.login(memberDtoRes);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @Operation(summary = "리이슈", description = "리이슈 및 토큰 재발급을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = TokenDto.class)))
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = TokenDto.class)))
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenReqDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccessWithNoData("0000"));
    }
}
