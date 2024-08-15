package fouragrant.scentasy.biz.member.repository;

import fouragrant.scentasy.biz.member.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);

    Refresh findByUsername(String username);

    @Transactional
    void deleteByRefresh(String refresh);
}
