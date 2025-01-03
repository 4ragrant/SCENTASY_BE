package fouragrant.scentasy.biz.post.controller;

import fouragrant.scentasy.biz.member.dto.MemberReqDto;
import fouragrant.scentasy.biz.post.dto.*;
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

import java.util.List;

@Slf4j
@Tag(name = "Comment", description = "Comment 관련 api")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create comment successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentReqDto.class)
            )
    )
    @PostMapping("/write/{postId}/{memberId}")
    public ResponseEntity<?> createComment(@PathVariable("postId") Long postId,@PathVariable("memberId") Long memberId, @RequestBody CommentReqDto commentReqDto) {
        CommentResDto commentResDto = commentService.createComment(postId, memberId, commentReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000",commentResDto));
    }

    @Operation(summary = "대댓글 생성", description = "대댓글 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create second comment successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentReqDto.class)
            )
    )
    @PostMapping("/second-write/{postId}/{memberId}")
    public ResponseEntity<?> createSecondComment(@PathVariable("postId") Long postId,@PathVariable("memberId") Long memberId, @RequestParam(required = false) Long parentCommentId, @RequestBody CommentReqDto commentReqDto) {
        CommentResDto commentResDto = commentService.createSecondComment(postId, memberId, parentCommentId, commentReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000",commentResDto));
    }

    @Operation(summary = "댓글 리스트 조회", description = "댓글 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get comment list successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentReqDto.class)
            )
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getCommentList(@PathVariable("postId") Long postId) {
        List<CommentListResDto> commentListResDto = commentService.getCommentList(postId);
        return ResponseEntity.ok(Response.createSuccess("0000", commentListResDto));
    }

    @Operation(summary = "댓글 수정", description = "댓글 수정을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "modify post successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommentReqDto.class)
            )
    )
    @PutMapping("/rewrite/{commentId}/{memberId}")
    public ResponseEntity<?> modifyComment(@PathVariable("commentId") Long commentId, @PathVariable("memberId") Long memberId, @RequestBody CommentReqDto commentReqDto) {
        CommentResDto commentResDto = commentService.modifyComment(commentId, memberId, commentReqDto);
        return ResponseEntity.ok(Response.createSuccess("0000", commentResDto));
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "delete post successfully",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @DeleteMapping("/delete/{commentId}/{memberId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId, @PathVariable("memberId") Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000", "delete successfully"));
    }


}
