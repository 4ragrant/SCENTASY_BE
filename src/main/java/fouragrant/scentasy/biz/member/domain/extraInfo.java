package fouragrant.scentasy.biz.member.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class extraInfo {
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
    @Enumerated(EnumType.STRING)
    @Column
    private Season season;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;



    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;
}
