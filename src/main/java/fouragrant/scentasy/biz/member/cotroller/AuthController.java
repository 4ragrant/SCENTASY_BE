package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.biz.member.dto.TokenReqDto;
import fouragrant.scentasy.biz.member.service.AuthService;
import fouragrant.scentasy.common.Response;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public  ResponseEntity<?> signup(@RequestBody MemberReqDto memberReqDto) {
            MemberResDto memberResDto = authService.signup(memberReqDto);
            return ResponseEntity.ok(Response.createSuccess("0000", memberResDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto memberDtoRes) {
        TokenDto tokenDto = authService.login(memberDtoRes);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccess("0000", tokenDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenReqDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        return ResponseEntity.ok(Response.createSuccessWithNoData("0000"));
    }
}
