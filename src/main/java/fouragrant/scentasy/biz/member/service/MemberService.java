package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor //생성자 주입
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResDto findMemberInfoById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberResDto findMemberInfoByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.FAILURE_LOGIN));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CommonException(ErrorCode.FAILURE_LOGIN));
    }

}
