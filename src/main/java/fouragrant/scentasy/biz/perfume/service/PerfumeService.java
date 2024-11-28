package fouragrant.scentasy.biz.perfume.service;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.domain.Accord;
import fouragrant.scentasy.biz.perfume.dto.AccordStatisticsResDto;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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

    public List<AccordStatisticsResDto> getMemberAccordStatistics(Long memberId) {
        List<Object[]> results = perfumeRepository.findAccordStatisticsByMemberId(memberId);

        // 결과가 없으면 예외 발생
        if (results.isEmpty()) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        // 결과를 DTO 리스트로 변환
        List<AccordStatisticsResDto> accordStatistics = results.stream()
                .map(result -> new AccordStatisticsResDto((String) result[0], (Long) result[1]))
                .toList();

        // 어코드가 7개 이하로 제한
        if (accordStatistics.size() > 7) {
            accordStatistics = accordStatistics.subList(0, 7);
        }

        return accordStatistics;
    }
}
