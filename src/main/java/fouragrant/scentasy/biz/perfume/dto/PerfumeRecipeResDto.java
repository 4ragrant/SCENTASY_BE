package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.member.domain.Scent;
import fouragrant.scentasy.biz.perfume.domain.Accord;

import java.util.List;

public record PerfumeRecipeResDto(
        List<String> notes,
        List<Accord> accords
) {
}
