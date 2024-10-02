package fouragrant.scentasy.biz.calendar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fouragrant.scentasy.biz.calendar.dto.MemoDto;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.common.dto.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.Text;

@Entity(name = "memo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memo")
public class Memo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_id")
    @JsonIgnore
    private Perfume perfume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    public Memo(String content, Member member, Perfume perfume) {
        this.content = content;
        this.member = member;
        this.perfume = perfume;
    }
}
