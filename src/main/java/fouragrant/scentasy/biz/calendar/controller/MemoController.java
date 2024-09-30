package fouragrant.scentasy.biz.calendar.controller;

import fouragrant.scentasy.biz.calendar.dto.MemoDto;
import fouragrant.scentasy.biz.calendar.service.MemoService;
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
@Tag(name = "Memo", description = "Memo 관련 api")
@RestController
@RequestMapping("/api/memo")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @Operation(summary = "메모 생성", description = "메모 생성을 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "create memo successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "메모 생성 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @PostMapping("/write/{memberId}/{perfumeId}")
    public ResponseEntity<?> createMemo(@PathVariable("memberId") Long memberId, @PathVariable("perfumeId") Long perfumeId, @RequestBody MemoDto memoDto){
        MemoDto memoResDto  = memoService.createMemo(memberId, perfumeId, memoDto.content());
        return ResponseEntity.ok(Response.createSuccess("0000",memoResDto));
    }
    @Operation(summary = "메모 조회", description = "메모 조회를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "get memo successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "메모 조회 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @GetMapping("/get/{memoId}")
    public ResponseEntity<?> getMemo(@PathVariable("memoId") Long memoId){
        MemoDto memoDto  = memoService.getMemo(memoId);
        return ResponseEntity.ok(Response.createSuccess("0000",memoDto));
    }
    @Operation(summary = "메모 삭제", description = "메모 삭제를 위한 메소드")
    @ApiResponse(responseCode = "0000", description = "delete memo successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "메모 삭제 요청 본문",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = MemoDto.class)
            )
    )
    @DeleteMapping("/delete/{memoId}/{memberId}")
    public ResponseEntity<?> deleteMemo(@PathVariable("memoId") Long memoId, @PathVariable("memoId") Long memberId){
        memoService.deleteMemo(memoId, memberId);
        return ResponseEntity.ok(Response.createSuccess("0000","delete successfully"));
    }
}
