package fouragrant.scentasy.biz.perfume.repository;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
    List<Perfume> findByMemberId(Long memberId);
    int countByMemberId(Long memberId);
}
