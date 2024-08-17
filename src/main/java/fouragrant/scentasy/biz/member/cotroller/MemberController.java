package fouragrant.scentasy.biz.member.cotroller;

import fouragrant.scentasy.biz.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
@Tag(name="Member", description = "Member API")
public class MemberController{
    private final MemberService memberService;


}
