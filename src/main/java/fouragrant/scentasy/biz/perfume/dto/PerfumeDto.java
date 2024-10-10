package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.perfume.domain.Accord;
import fouragrant.scentasy.biz.perfume.domain.Perfume;

import java.time.LocalDateTime;
import java.util.List;

public record PerfumeDto(
        String title,
        String description,
        List<Accord> accords,
        List<Note> notes,
        LocalDateTime createdAt
) {
    // NoteResponse 클래스 정의
    public record Note(String note_en, String note_kr) {}

    // Perfume 엔티티를 PerfumeDto로 변환
    public static PerfumeDto fromEntity(Perfume perfume) {
        // Scent 리스트를 NoteResponse 리스트로 변환
        List<Note> noteResponses = perfume.getNotes().stream()
                .map(scent -> new Note(scent.name(), scent.getDescription()))
                .toList();

        return new PerfumeDto(
                perfume.getTitle(),
                perfume.getDescription(),
                perfume.getAccords(),
                noteResponses,
                perfume.getCreatedAt()
        );
    }
}
