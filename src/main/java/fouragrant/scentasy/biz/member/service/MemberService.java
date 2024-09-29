package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.MemberResDto;
import fouragrant.scentasy.biz.member.repository.MemberRepository;
import fouragrant.scentasy.biz.mypage.service.S3Service;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor //생성자 주입
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

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

    public Member updateProfileImage(Long memberId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty() || memberId == null) {
            throw new CommonException(ErrorCode.MISSING_PARAMETER);
        }

        Member member = findById(memberId);

        String imageName = memberId.toString();
        String imageUrl = s3Service.upload(imageName, file);

        member.setImageName(imageName);
        member.setImageUrl(imageUrl);

        return memberRepository.save(member);
    }

}
