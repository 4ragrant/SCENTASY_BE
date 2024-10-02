package fouragrant.scentasy.biz.calendar.repository;

import fouragrant.scentasy.biz.calendar.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByPerfume_PerfumeId(Long perfumeId);
}
