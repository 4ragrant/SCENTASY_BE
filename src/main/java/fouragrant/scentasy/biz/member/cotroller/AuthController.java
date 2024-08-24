package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.biz.member.dto.TokenReqDto;
import fouragrant.scentasy.biz.member.service.AuthService;
import fouragrant.scentasy.jwt.JwtBlacklist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberResDto> signup(@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(authService.signup(memberReqDto));
    }
    @Operation(summary = "로그인", description = "로그인을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto memberDtoRes) {
        return ResponseEntity.ok(authService.login(memberDtoRes));
    }
    @Operation(summary = "토큰재발급", description = "토큰재발급을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
    @Operation(summary = "로그아웃", description = "로그아웃을 위한 메소드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)))
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> logout(@RequestBody TokenReqDto tokenRequestDto) {
        return ResponseEntity.ok(authService.logout(tokenRequestDto));
    }
}
