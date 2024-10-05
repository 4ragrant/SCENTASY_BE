package fouragrant.scentasy.biz.member.repository;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraInfoRepository extends JpaRepository<ExtraInfo, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByMember(Member member);
    ExtraInfo findByMemberId(Long memberId);
}
