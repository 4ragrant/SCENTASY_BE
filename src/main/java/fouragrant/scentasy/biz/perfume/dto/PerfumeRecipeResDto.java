package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.member.domain.Scent;
import java.util.List;

public record PerfumeRecipeResDto(
        List<Scent> notes
) {
}
