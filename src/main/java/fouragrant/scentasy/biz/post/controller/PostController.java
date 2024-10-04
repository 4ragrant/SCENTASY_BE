package fouragrant.scentasy.biz.post.controller;

import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.service.PostService;
import fouragrant.scentasy.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    mediaType = "application/json"
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/list")
    public ResponseEntity<Response<List<PostResDto>>> getPostList() {
        List<PostResDto> postList = postService.getPostList();
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }


    @Operation(summary = "인기 있는 포스트 리스트 조회", description = "인기있는 포스트 리스트 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get hot post list successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/list-top3")
    public ResponseEntity<Response<List<PostResDto>>> getTopPostList() {
        List<PostResDto> postList = postService.getTopPostList();
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }


    @Operation(summary = "포스트 생성", description = "포스트 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create post successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @PostMapping("/write/{memberId}/{perfumeId}")
    public ResponseEntity<?> createPost(@PathVariable("memberId") Long memberId, @PathVariable("perfumeId") Long perfumeId,  @RequestBody PostReqDto postReqDto) {
        PostResDto postResDto  = postService.createPost(postReqDto, memberId, perfumeId);
        return ResponseEntity.ok(Response.createSuccess("0000",postResDto));
    }


    @Operation(summary = "포스트 상세 조희", description = "포스트 상세 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get particular post successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId) {
        PostResDto postResDto  = postService.getPost(postId);
        return ResponseEntity.ok(Response.createSuccess("0000",postResDto));
    }


    @Operation(summary = "포스트 수정", description = "포스트 수정을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "modify post successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @PutMapping("/modify/{postId}/{memberId}/{perfumeId}")
    public ResponseEntity<?> modifyPost(@PathVariable("postId") Long postId, @PathVariable("memberId") Long memberId, @PathVariable("perfumeId") Long perfumeId, @RequestBody PostReqDto postReqDto) {
        PostResDto postResDto  = postService.modifyPost(postId, memberId, perfumeId, postReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000",postResDto));
    }

    @Operation(summary = "포스트 삭제", description = "포스트 삭제 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "delete post successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @DeleteMapping("/delete/{postId}/{memberId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId, @PathVariable("memberId") Long memberId) {
        postService.deletePost(postId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", "delete successfully"));
    }

    @Operation(summary = "포스트 좋아요 생성", description = "포스트 좋아요 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create post-like successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @PostMapping("/create-postlike/{postId}/{memberId}")
    public ResponseEntity<?> createPostlike(@PathVariable("postId") Long postId, @PathVariable("memberId") Long memberId) {
        PostResDto postResDto = postService.createPostlike(postId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000",postResDto));
    }

    @Operation(summary = "포스트 좋아요 삭제", description = "포스트 좋아요 삭제를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "delete post-like successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @DeleteMapping("/delete-postlike/{postId}/{memberId}")
    public ResponseEntity<?> deletePostLike(@PathVariable("postId") Long postId, @PathVariable("memberId") Long memberId) {
        postService.deletePostLike(postId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000","delete post-like successfully"));
    }

    @Operation(summary = "사용자 포스트 리스트 조회", description = "사용자의 포스트 리스트 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get host's post list successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/list/{memberId}")
    public ResponseEntity<Response<List<PostResDto>>> getHostPostList(@PathVariable("memberId") Long memberId) {
        List<PostResDto> postList = postService.getHostPostList(memberId);
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }

    @Operation(summary = "사용자 좋아요 포스트 리스트 조회", description = "사용자가 좋아요를 누른 포스트 리스트 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get host's liked-post list successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(responseCode = "4500", description = "포스트가 존재하지 않습니다.")
    @GetMapping("/like-list/{memberId}")
    public ResponseEntity<Response<List<PostResDto>>> getLikePostList(@PathVariable("memberId") Long memberId) {
        List<PostResDto> postList = postService.getLikePostList(memberId);
        return ResponseEntity.ok(Response.createSuccess("0000",postList));
    }
}
