package fouragrant.scentasy.biz.perfume.service;

import fouragrant.scentasy.biz.perfume.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfumeDeviceService {
    private final PerfumeRepository perfumeRepository;
    public String getArrayByPerfumeId(Long perfumeId) {
        return perfumeRepository.findRecipeArrayByPerfumeId(perfumeId);
    }

}
