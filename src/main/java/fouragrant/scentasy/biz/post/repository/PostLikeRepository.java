package fouragrant.scentasy.biz.post.repository;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndMember(Post post, Member member);

    @Query("SELECT pl.post FROM PostLike pl WHERE pl.member = :member")
    List<Post> findPostsLikedByMember(@Param("member") Member member);
}
