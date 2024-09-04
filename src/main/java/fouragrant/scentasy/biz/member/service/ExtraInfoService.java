package fouragrant.scentasy.biz.member.service;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.repository.ExtraInfoRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtraInfoService {
    private final ExtraInfoRepository repository;

    // 추가정보 저장
    public ExtraInfo saveExtraInfo(ExtraInfoReqDto extraInfoReqDto, Member member) {
        if (member == null) {
            throw new CommonException(ErrorCode.FAILURE_LOGIN);
        }

        if (repository.existsByMember(member)) {
            throw new CommonException(ErrorCode.EXTRA_INFO_DUPLICATED);
        }

        if (repository.existsByNickname(extraInfoReqDto.getNickname())) {
            throw new CommonException(ErrorCode.NICKNAME_DUPLICATED);
        }

        ExtraInfo extraInfo = ExtraInfo.fromDto(extraInfoReqDto, member);
        member.setExtraInfo(extraInfo);

        return repository.save(extraInfo);
    }

    // 추가정보 수정
    public ExtraInfo updateExtraInfo(Long id, ExtraInfoReqDto extraInfoReqDto, Member member) {
        ExtraInfo extraInfo = repository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.EXTRA_INFO_NOT_FOUND));

        if (extraInfoReqDto.getNickname() != null) {
            if (!extraInfo.getNickname().equals(extraInfoReqDto.getNickname()) &&
                    repository.existsByNickname(extraInfoReqDto.getNickname())) {
                throw new CommonException(ErrorCode.NICKNAME_DUPLICATED);
            }
            extraInfo.setNickname(extraInfoReqDto.getNickname());
        }

        if (extraInfoReqDto.getGender() != null) {
            extraInfo.setGender(extraInfoReqDto.getGender());
        }

        if (extraInfoReqDto.getAge() != null) {
            extraInfo.setAge(extraInfoReqDto.getAge());
        }

        if (extraInfoReqDto.getSeason() != null) {
            extraInfo.setSeason(extraInfoReqDto.getSeason());
        }

        if (extraInfoReqDto.getLikedScents() != null) {
            extraInfo.setLikedScents(extraInfoReqDto.getLikedScents());
        }

        if (extraInfoReqDto.getDislikedScents() != null) {
            extraInfo.setDislikedScents(extraInfoReqDto.getDislikedScents());
        }

        return repository.save(extraInfo);
    }

    // 닉네임 중복 확인
    public boolean checkNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

}
