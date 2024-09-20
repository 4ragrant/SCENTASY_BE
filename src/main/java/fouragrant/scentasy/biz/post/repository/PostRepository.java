package fouragrant.scentasy.biz.post.repository;

import fouragrant.scentasy.biz.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN p.postLikes pl GROUP BY p ORDER BY COUNT(pl) DESC")
    List<Post> findTop3ByOrderByLikeCountDesc();

    List<Post> findByMemberId(Long memberId);
}
