package fouragrant.scentasy.biz.perfume.repository;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

}
