package fouragrant.scentasy.biz.perfume.service;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    // 향수 ID로 향수 상세 정보 조회
    public Perfume findPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new CommonException(ErrorCode.PERFUME_NOT_FOUND));
    }

    // 멤버 향수 전체 목록 조회
    public List<Perfume> getMemberPerfumes(Long memberId) {
        List<Perfume> perfumes = perfumeRepository.findByMemberId(memberId);

        if (perfumes.isEmpty()) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        return perfumes;
    }

    // 멤버 향수 전체 개수 조회
    public int getMemberPerfumeCount(Long memberId) {
        return perfumeRepository.countByMemberId(memberId);
    }
}
