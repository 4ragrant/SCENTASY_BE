package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.domain.Scent;
import fouragrant.scentasy.biz.perfume.domain.Perfume;

import java.util.List;

public record PerfumeDto(
        String title,
        String description,
        List<String> accords,
        List<Scent> notes
) {
    // Perfume 엔티티를 PerfumeDto로 변환
    public static PerfumeDto fromEntity(Perfume perfume) {
        return new PerfumeDto(
                perfume.getTitle(),
                perfume.getDescription(),
                perfume.getAccords(),
                perfume.getNotes()
        );
    }

    // PerfumeDto를 Perfume 엔티티로 변환
    public static Perfume fromDto(PerfumeDto dto, Member member) {
        return Perfume.builder()
                .title(dto.title())
                .description(dto.description())
                .accords(dto.accords())
                .notes(dto.notes())
                .member(member)
                .build();
    }
}
