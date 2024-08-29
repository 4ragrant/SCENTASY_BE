package fouragrant.scentasy.biz.member.domain;

import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name="extra_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtraInfo extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extra_info_id")
    private Long id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "member_nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column
    private Season season;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column
    private Age age;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "extraInfo", fetch = FetchType.LAZY)
    private Member member;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "liked_scents", joinColumns = @JoinColumn(name = "extra_info_id"))
    @Column(name = "scent")
    private List<Scent> likedScents;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "disliked_scents", joinColumns = @JoinColumn(name = "extra_info_id"))
    @Column(name = "scent")
    private List<Scent> dislikedScents;

    // ExtraInfoReqDto를 ExtraInfo로 변환
    public static ExtraInfo fromDto(ExtraInfoReqDto dto, Member member) {
        return ExtraInfo.builder()
                .nickname(dto.getNickname())
                .season(dto.getSeason())
                .gender(dto.getGender())
                .age(dto.getAge())
                .likedScents(dto.getLikedScents())
                .dislikedScents(dto.getDislikedScents())
                .member(member)
                .build();
    }

    // ExtraInfo를 ExtraInfoDto로 변환
    public ExtraInfoReqDto toDto() {
        return ExtraInfoReqDto.builder()
                .nickname(this.nickname)
                .season(this.season)
                .gender(this.gender)
                .age(this.age)
                .likedScents(this.likedScents)
                .dislikedScents(this.dislikedScents)
                .build();
    }
}
