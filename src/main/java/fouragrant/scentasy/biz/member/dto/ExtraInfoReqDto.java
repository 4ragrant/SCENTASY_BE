package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.Age;
import fouragrant.scentasy.biz.member.domain.Gender;
import fouragrant.scentasy.biz.member.domain.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExtraInfoReqDto {
    private String nickname;
    private Gender gender;
    private Age age;
    private Season season;

}
