package fouragrant.scentasy.biz.post.controller;

import fouragrant.scentasy.biz.member.dto.ExtraInfoResDto;
import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.service.PostService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@Slf4j
@Tag(name = "Post", description = "Post 관련 api")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @Operation(summary = "포스트 리스트 조회", description = "포스트 리스트 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get post list successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/")
    public ResponseEntity<Response<List<PostResDto>>> getPostList() {
        List<PostResDto> postList = postService.getPostList();
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }
    @Operation(summary = "인기있는 포스트 리스트 조회", description = "인기있는 포스트 리스트 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get hot post list successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/top3")
    public ResponseEntity<Response<List<PostResDto>>> getTopPostList() {
        List<PostResDto> postList = postService.getTopPostList();
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }


}
