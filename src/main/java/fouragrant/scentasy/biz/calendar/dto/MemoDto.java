package fouragrant.scentasy.biz.calendar.dto;

import fouragrant.scentasy.biz.calendar.domain.Memo;

public record MemoDto(
        String content
){
    public static MemoDto toMemoDto(Memo memo){
        return new MemoDto(
                memo.getContent()
        );
    }
}
