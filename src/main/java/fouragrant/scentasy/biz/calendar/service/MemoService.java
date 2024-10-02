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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public MemoDto getMemo(Long memoId) {
        Optional<Memo> memoOptional = memoRepository.findById(memoId);
        if (memoOptional.isEmpty()){
            throw new CommonException(ErrorCode.MEMO_NOT_FOUND);
        }
        Memo memo = memoOptional.get();

        return toMemoDto(memo);
    }

    public List<MemoDto> getMemoList(Long perfumeId) {
        List<Memo> memoList = memoRepository.findByPerfume_PerfumeId(perfumeId);
        if(memoList == null){
            throw new CommonException(ErrorCode.MEMO_NOT_FOUND);
        }
        return memoList.stream()
                .map(MemoDto::toMemoDto)
                .collect(Collectors.toList());
    }

    public void deleteMemo(Long memoId, Long memberId) {
        Optional<Memo> memoOptional = memoRepository.findById(memoId);
        if (memoOptional.isEmpty()){
            throw new CommonException(ErrorCode.MEMO_NOT_FOUND);
        }
        Memo memo = memoOptional.get();

        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 회원이 다르면 예외 던짐
        if (member != memo.getMember()) {
            throw new CommonException(ErrorCode.MEMBER_NOT_SAME);
        }

        memoRepository.delete(memo);
    }



}
