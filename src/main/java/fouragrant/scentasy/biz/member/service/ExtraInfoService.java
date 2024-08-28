package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.repository.ExtraInfoRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtraInfoService {
    private final ExtraInfoRepository repository;

    public ExtraInfo saveExtraInfo(ExtraInfoReqDto extraInfoReqDto, Member member) {
        if (repository.existsByNickname(extraInfoReqDto.getNickname())) {
            throw new CommonException(ErrorCode.NICKNAME_DUPLICATED);
        }

        ExtraInfo extraInfo = ExtraInfo.fromDto(extraInfoReqDto, member);
        return repository.save(extraInfo);
    }

    public boolean checkNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

}
