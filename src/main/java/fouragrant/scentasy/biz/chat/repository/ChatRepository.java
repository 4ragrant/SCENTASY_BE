package fouragrant.scentasy.biz.chat.repository;

import fouragrant.scentasy.biz.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
