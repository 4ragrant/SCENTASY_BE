package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.domain.MemberStatus;
import fouragrant.scentasy.biz.member.domain.RefreshToken;
import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.biz.member.dto.TokenReqDto;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.biz.member.repository.RefreshTokenRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import fouragrant.scentasy.jwt.JwtBlacklist;
import fouragrant.scentasy.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtBlacklist jwtBlacklist;

    @Transactional
    public MemberResDto signup(MemberReqDto memberReqDto) {
        if (memberRepository.existsByEmail(memberReqDto.getEmail())) {
            throw new CommonException(ErrorCode.EMAIL_DUPLICATED);
        }
        Member member = memberReqDto.toMember(passwordEncoder);
        return MemberResDto.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(MemberReqDto memberReqDto) {
        try {// 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = memberReqDto.toAuthentication();

            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 발급
            return tokenDto;
        } catch (BadCredentialsException e) {
            // 비밀번호가 맞지 않는 경우 예외 처리
            throw new CommonException(ErrorCode.FAILURE_LOGIN);
        } catch (UsernameNotFoundException e) {
            // 이메일이 등록되지 않은 경우 예외 처리
            throw new CommonException(ErrorCode.FAILURE_LOGIN);
        } catch (CommonException e){
            // 토큰 만료 시 예외 처리
            throw new CommonException(ErrorCode.TOKEN_EXPIRED);
        }
    }


    @Transactional
    public TokenDto reissue(TokenReqDto tokenReqDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    public String logout(TokenReqDto tokenRequestDto) {
        // TokenReqDto에서 Access Token 추출
        String accessToken = tokenRequestDto.getAccessToken();

        // "Bearer " 문자열 제거 (필요한 경우)
        String pureToken = accessToken.replace("Bearer ", "");

        // 토큰을 블랙리스트에 추가
        jwtBlacklist.addTokenToBlacklist(pureToken);

        // 로그아웃 성공 메시지 반환
        return "Logged out successfully.";
    }

    @Transactional
    public void activateAccount(Member member) {
        member.setStatus(MemberStatus.ACTIVE); // 상태를 ACTIVE로 변경
        memberRepository.save(member);
    }

}
