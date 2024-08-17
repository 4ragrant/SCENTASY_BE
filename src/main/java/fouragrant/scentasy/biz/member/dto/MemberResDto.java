package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.Member;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDto {
    private String email;

    public static MemberResDto of(Member member) {
        return new MemberResDto(member.getEmail());
    }

}
