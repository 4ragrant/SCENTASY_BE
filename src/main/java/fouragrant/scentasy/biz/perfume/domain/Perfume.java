package fouragrant.scentasy.biz.perfume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fouragrant.scentasy.biz.calendar.domain.Memo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

import java.util.List;
import org.springframework.data.annotation.CreatedDate;

@Entity(name = "perfume")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "perfume")
public class Perfume extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long perfumeId;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

//    @Column(name = "recipe_array")
//    private String recipeArray;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ElementCollection
    @CollectionTable(name = "perfume_accords", joinColumns = @JoinColumn(name = "perfume_id"))
    private List<Accord> accords;

    @ElementCollection
    @CollectionTable(name = "perfume_notes", joinColumns = @JoinColumn(name = "perfume_id"))
    @Column(name = "notes")
    private List<Scent> notes;

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Memo> memos;
}
