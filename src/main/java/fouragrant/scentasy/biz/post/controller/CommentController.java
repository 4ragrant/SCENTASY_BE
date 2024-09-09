package fouragrant.scentasy.biz.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Comment", description = "Comment 관련 api")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

}
