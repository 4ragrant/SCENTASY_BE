package fouragrant.scentasy.biz.post.repository;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndMember(Post post, Member member);
}
