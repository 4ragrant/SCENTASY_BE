package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.domain.ExtraInfo;
import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoReqDto;
import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.member.service.ExtraInfoService;
import fouragrant.scentasy.biz.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/extrainfo")
public class ExtraInfoController {
    private final ExtraInfoService extraInfoService;
    private final MemberService memberService;

    @GetMapping
    public String test() {
        return "success";
    }

    @PostMapping
    public ResponseEntity<ExtraInfoResDto> createExtraInfo(@RequestBody ExtraInfoReqDto extraInfoReqDto, @RequestParam("email") String email) {
        Member member = memberService.findByEmail(email);
        if(member == null) {
            return ResponseEntity.badRequest().body(new ExtraInfoResDto<>("Member not found", null));
        }

        ExtraInfo createdExtraInfo = extraInfoService.saveExtraInfo(extraInfoReqDto, member);
        ExtraInfoResDto response = new ExtraInfoResDto(null, List.of(createdExtraInfo));

        return ResponseEntity.ok(response);
    }
}
