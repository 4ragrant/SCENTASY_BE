package fouragrant.scentasy.biz.chat.repository;

import fouragrant.scentasy.biz.chat.domain.Chat;
import fouragrant.scentasy.biz.chat.dto.ChatListResDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT DISTINCT FUNCTION('DATE', c.createdAt) FROM chat c WHERE c.member.id = :memberId")
    List<java.sql.Date> findDistinctChatDatesByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT c FROM chat c WHERE c.member.id = :memberId AND c.createdAt BETWEEN :startOfDay AND :endOfDay")
    List<Chat> findChatsByMemberIdAndDate(@Param("memberId") Long memberId,
                                                    @Param("startOfDay") LocalDateTime startOfDay,
                                                    @Param("endOfDay") LocalDateTime endOfDay);
}


