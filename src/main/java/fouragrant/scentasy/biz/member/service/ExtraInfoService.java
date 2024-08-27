package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.repository.ExtraInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtraInfoService {
    private final ExtraInfoRepository repository;

    public ExtraInfo saveExtraInfo(ExtraInfoReqDto extraInfoReqDto, Member member) {
        ExtraInfo extraInfo = ExtraInfo.fromDto(extraInfoReqDto, member);
        return repository.save(extraInfo);
    }

    public boolean checkNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

}
