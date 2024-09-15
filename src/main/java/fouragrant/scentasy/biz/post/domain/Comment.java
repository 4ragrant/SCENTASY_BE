package fouragrant.scentasy.biz.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.post.dto.CommentReqDto;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;                 // 댓글 ID

    @Column(name = "content", nullable = false)
    private String content;                 // 댓글 내용

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;                      // 해당 게시물

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;                  // 댓글 작성자

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parentComment; //부모 댓글

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childrenComment = new ArrayList<>(); //자식 댓글들(대댓글)


    // 생성자
    public Comment(Post post, Member member, String content) {
        this.post = post;
        this.member = member;
        this.content = content;
    }

    public Comment(Post post, Member member, Comment parentComment, String content) {
        this.post = post;
        this.member = member;
        this.content = content;
        this.parentComment= parentComment;
    }


}
