package fouragrant.scentasy.biz.post.controller;

import fouragrant.scentasy.biz.post.dto.CommentReqDto;
import fouragrant.scentasy.biz.post.dto.CommentResDto;
import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.service.CommentService;
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

@Slf4j
@Tag(name = "Comment", description = "Comment 관련 api")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create post successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @PostMapping("/write/{postId}/{memberId}")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,@PathVariable("memberId") Long memberId, @RequestBody CommentReqDto commentReqDto) {
        CommentResDto commentResDto = commentService.createComment(postId, memberId, commentReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000",commentResDto));
    }

    @Operation(summary = "댓글 생성", description = "댓글 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create post successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostReqDto.class)
            )
    )
    @PostMapping("/second-write/{postId}/{memberId}")
    public ResponseEntity<?> createSecondComment(@PathVariable("postId") Long postId,@PathVariable("memberId") Long memberId, @RequestParam(required = false) Long parentCommentId, @RequestBody CommentReqDto commentReqDto) {
        CommentResDto commentResDto = commentService.createSecondComment(postId, memberId, parentCommentId, commentReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000",commentResDto));
    }
}
