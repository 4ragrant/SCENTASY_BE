package fouragrant.scentasy.biz.perfume.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfumeService {
    private final PerfumeRepository perfumeRepository;

    // 생성된 향수 저장
    public Perfume createPerfume(PerfumeDto perfumeDto, Member member) {
        Perfume perfume = Perfume.fromDto(perfumeDto, member);

        return perfumeRepository.save(perfume);
    }


}
