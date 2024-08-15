package fouragrant.scentasy.biz.member.service.member;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.MemberDtoReq;
import fouragrant.scentasy.biz.member.repository.ExtraInfoRepository;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.biz.member.repository.RefreshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import fouragrant.scentasy.jwt.JWTUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor //생성자 주입
public class MemberService {
    private static long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000; // 30일을 밀리초로 변환
    private final MemberRepository memberRepository;
    private final ExtraInfoRepository extraInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration configuration;
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    public void emailCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()){
            throw new RuntimeException("이미 존재하는 이메일입니다");
        }
    }

    public Member signUp(MemberDtoReq.SignUpDto signUpDto){
        //이미 가입이 되어 있으면 가입 취소
        if (memberRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }
        // member 엔티티 생성
        String encodedPw = passwordEncoder.encode(signUpDto.getPassword());
        signUpDto.setPassword((encodedPw));
        Member member = Member.createMember(signUpDto, encodedPw);

        // 저장
        return memberRepository.save(member);

    }

    public void extraInfo(MemberDtoReq.ExtraInfoDto extraInfoDto) {
    }
}
