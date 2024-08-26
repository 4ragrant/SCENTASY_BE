package fouragrant.scentasy.biz.member.domain;

import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtraInfo extends BaseTimeEntity {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "extrainfo_id")
    private Long extrainfoId;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;

    // ExtraInfoReqDto를 ExtraInfo로 변환
    public static ExtraInfo fromDto(ExtraInfoReqDto dto, Member member) {
        return ExtraInfo.builder()
                .nickname(dto.getNickname())
                .season(dto.getSeason())
                .gender(dto.getGender())
                .age(dto.getAge())
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
                .build();
    }
}
