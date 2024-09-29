package fouragrant.scentasy.biz.calendar.repository;

import fouragrant.scentasy.biz.calendar.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
