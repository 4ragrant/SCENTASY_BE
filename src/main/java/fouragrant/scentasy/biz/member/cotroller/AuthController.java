package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.biz.member.dto.TokenReqDto;
import fouragrant.scentasy.biz.member.service.AuthService;
import fouragrant.scentasy.common.Response;
import fouragrant.scentasy.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @ApiResponse(responseCode = "0000", description = "signup successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemberResDto.class)
            )
    )
    @ApiResponse(responseCode = "4201", description = "존재하는 이메일입니다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회원가입 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = MemberReqDto.class)
            )
    )
    @PostMapping("/signup")
    public  ResponseEntity<?> signup(@RequestBody @Valid MemberReqDto memberReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Response.createError(ErrorCode.VALIDATION_FAILED.getCode(), ErrorCode.VALIDATION_FAILED.getMessage()));
        }
        MemberResDto memberResDto = authService.signup(memberReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000", memberResDto));
    }

    @Operation(summary = "로그인", description = "로그인 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "login successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenDto.class)
            )
    )
    @ApiResponse(responseCode = "4300", description = "잘못된 이메일 또는 비밀번호입니다")
    @ApiResponse(responseCode = "4301", description = "추가정보가 존재하지 않습니다")
    @ApiResponse(responseCode = "4400", description = "만료된 토큰입니다")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "로그인 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = MemberReqDto.class)
            )
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto memberDtoRes) {
        TokenDto tokenDto = authService.login(memberDtoRes);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @Operation(summary = "리이슈", description = "리이슈 및 토큰 재발급을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "login successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenDto.class)
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "리이슈 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = TokenReqDto.class)
            )
    )
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "logout successfully")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "로그아웃 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = TokenReqDto.class)
            )
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenReqDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccessWithNoData("0000"));
    }
}
