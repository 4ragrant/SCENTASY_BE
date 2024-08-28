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
    public Response<TokenDto> login(@RequestBody MemberReqDto memberDtoRes) {
        return Response.createSuccess("0000", authService.login(memberDtoRes));
    }

    @PostMapping("/reissue")
    public Response<TokenDto> reissue(@RequestBody TokenReqDto tokenRequestDto) {
        return Response.createSuccess("0000", authService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public Response<String> logout(@RequestBody TokenReqDto tokenRequestDto) {
        return Response.createSuccess("0000", authService.logout(tokenRequestDto));
    }
}
