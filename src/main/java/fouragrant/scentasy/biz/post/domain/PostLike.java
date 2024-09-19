package fouragrant.scentasy.biz.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fouragrant.scentasy.biz.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                    // 좋아요 ID

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;                      // 해당 게시물

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;                  // 좋아요 한 사용자

    // 생성자
    public PostLike(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
