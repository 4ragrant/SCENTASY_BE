package fouragrant.scentasy.biz.perfume.dto;

import java.util.List;

public record PerfumeDto(
        String title,
        String description,
        List<String> accords,
        List<String> notes
) {

}
