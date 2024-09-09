package fouragrant.scentasy.biz.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */

    @Column(name = "post_title")
    @NotBlank
    private String title;

    @Column(name = "post_content")
    @NotBlank
    private String content;

    @Column(name = "view_count")
    private int viewCount;


    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_id")
    @JsonIgnore
    private Perfume perfume;  // 해당 게시물에 사용된 향수

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes;

    // 생성자
    public Post(PostReqDto postReqDto, Member member, Perfume perfume) {
        this.title = postReqDto.getTitle();
        this.content = postReqDto.getContent();
        this.member = member;
        this.perfume = perfume;
        this.viewCount = 0; // 초기 조회수는 0으로 설정
        this.comments = new ArrayList<>();  // 빈 리스트로 초기화
        this.postLikes = new ArrayList<>(); // 빈 리스트로 초기화
    }

    // 좋아요 개수 반환 메소드
    public int getLikeCount() {
        return postLikes.size();
    }

    // 댓글 개수 반환 메소드
    public int getCommentCount() {
        return comments.size();
    }

    // 조회수 증가 메소드
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updatePost(String title, String content, Perfume perfume) {
        this.title = title;       // 제목 수정
        this.content = content;   // 내용 수정
        this.perfume = perfume;   // 향수 정보 수정
    }
}
