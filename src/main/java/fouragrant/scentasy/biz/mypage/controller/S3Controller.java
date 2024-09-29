package fouragrant.scentasy.biz.mypage.controller;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.mypage.service.S3Service;
import fouragrant.scentasy.common.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3Service;
    private final MemberService memberService;

    @PostMapping("/image")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("memberId") Long memberId,
            @RequestParam("file") MultipartFile file ) throws IOException {
        Member member = memberService.updateProfileImage(memberId, file);
        return ResponseEntity.ok(Response.createSuccess("0000", member.getImageUrl()));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Response.createSuccess("0000", "테스트"));
    }
}
