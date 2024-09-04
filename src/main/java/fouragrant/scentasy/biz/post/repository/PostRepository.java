package fouragrant.scentasy.biz.post.repository;

import fouragrant.scentasy.biz.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
