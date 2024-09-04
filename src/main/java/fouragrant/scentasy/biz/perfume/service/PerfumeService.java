package fouragrant.scentasy.biz.perfume.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfumeService {
    private final PerfumeRepository perfumeRepository;

    // 생성된 향수 저장
    public Perfume createPerfume(PerfumeDto perfumeDto, Member member) {
        Perfume perfume = PerfumeDto.fromDto(perfumeDto, member);

        return perfumeRepository.save(perfume);
    }

    // 향수 ID로 향수 상세 정보 조회
    public Perfume findPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new CommonException(ErrorCode.PERFUME_NOT_FOUND));
    }

    // 멤버 ID로 향수 전체 목록 조회
    public List<Perfume> findPerfumesByMemberId(Long memberId) {
         return perfumeRepository.findByMemberId(memberId)
                 .orElseThrow(() -> new CommonException(ErrorCode.PERFUME_NOT_FOUND));
    }
}
