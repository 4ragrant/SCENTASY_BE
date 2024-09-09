package fouragrant.scentasy.biz.post.repository;

import fouragrant.scentasy.biz.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
