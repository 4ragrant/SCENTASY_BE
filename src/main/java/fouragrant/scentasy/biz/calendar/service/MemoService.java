package fouragrant.scentasy.biz.calendar.service;

import fouragrant.scentasy.biz.calendar.domain.Memo;
import fouragrant.scentasy.biz.calendar.dto.MemoDto;
import fouragrant.scentasy.biz.calendar.repository.MemoRepository;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.service.PerfumeService;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static fouragrant.scentasy.biz.calendar.dto.MemoDto.toMemoDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemberService memberService;
    private final PerfumeService perfumeService;
    private final MemoRepository memoRepository;


    public MemoDto createMemo(Long memberId, Long perfumeId, String content) {
        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Perfume perfume = perfumeService.findPerfumeById(perfumeId);
        // 향수가 없으면 예외 던짐
        if (perfume == null) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        Memo memo = new Memo(content, member, perfume);

        memoRepository.save(memo);

        return toMemoDto(memo);
    }
}
