package fouragrant.scentasy.biz.chat.repository;

import fouragrant.scentasy.biz.chat.domain.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findChatsBySessionIdAndMemberId(String sessionId, Long memberId);

    @Query("SELECT DISTINCT c.sessionId FROM chat c WHERE c.member.id = :memberId")
    List<String> findDistinctSessionIdsByMemberId(Long memberId);

    List<Chat> findBySessionIdStartingWith(String sessionIdPrefix);
}


