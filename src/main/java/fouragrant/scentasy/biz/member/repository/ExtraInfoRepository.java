package fouragrant.scentasy.biz.member.repository;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraInfoRepository extends JpaRepository<ExtraInfo, Long> {
    boolean existsByNickname(String nickname);
}
