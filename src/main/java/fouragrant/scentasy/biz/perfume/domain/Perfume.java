package fouragrant.scentasy.biz.perfume.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.perfume.dto.PerfumeDto;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @ElementCollection
    @CollectionTable(name = "perfume_accords", joinColumns = @JoinColumn(name = "perfume_id"))
    @Column(name = "accords")
    private List<String> accords;

    @ElementCollection
    @CollectionTable(name = "perfume_notes", joinColumns = @JoinColumn(name = "perfume_id"))
    @Column(name = "notes")
    private List<String> notes;

    // PerfumeDto를 Perfume으로 변환
    public static Perfume fromDto(PerfumeDto dto, Member member) {
        return Perfume.builder()
                .title(dto.title())
                .description(dto.description())
                .accords(dto.accords())
                .notes(dto.notes())
                .member(member)
                .build();
    }
}
