package fouragrant.scentasy.biz.mypage.controller;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.mypage.service.S3Service;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "S3", description = "S3 관련 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
    private final S3Service s3Service;
    private final MemberService memberService;

    @Operation(
            summary = "프로필 사진 등록",
            description = "멤버의 프로필 사진을 등록"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "프로필 사진이 등록 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 ID", required = true, example = "1"),
            @Parameter(name = "file", description = "업로드할 이미지 파일", required = true)
    })
    @PostMapping("/profile-image")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("memberId") Long memberId,
            @RequestParam("file") MultipartFile file) throws IOException {
        Member member = memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok(Response.createSuccess("0000", member.getImageUrl()));
    }

    @Operation(
            summary = "향기 사진 조회",
            description = "요청된 향기 이름에 해당하는 이미지 URL을 반환"
    )
    @ApiResponse(
            responseCode = "0000",
            description = "향기 이미지 URL 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            )
    )
    @Parameters({
            @Parameter(
                    name = "scents",
                    description = "조회할 향기 이름 목록",
                    required = true,
                    example = "[\"aldehyde_C18\", \"amber\", \"aqua\"]"
            )
    })
    @GetMapping("/scent-images")
    public ResponseEntity<?> getScentImages(@RequestParam("scents") List<String> scents) {
        List<String> imageUrls = s3Service.getScentImages(scents);
        return ResponseEntity.ok(Response.createSuccess("0000", imageUrls));
    }
}
