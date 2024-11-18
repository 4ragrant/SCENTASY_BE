package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.Age;
import fouragrant.scentasy.biz.member.domain.Gender;
import fouragrant.scentasy.biz.perfume.domain.Scent;
import fouragrant.scentasy.biz.member.domain.Season;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExtraInfoReqDto {
    private String nickname;
    private Gender gender;
    private Age age;
    private Season season;

    @Size(min = 5, max = 5)
    private List<Scent> likedScents;

    @Size(min = 5, max = 5)
    private List<Scent> dislikedScents;
}
