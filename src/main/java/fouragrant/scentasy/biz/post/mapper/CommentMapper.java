package fouragrant.scentasy.biz.post.mapper;

import fouragrant.scentasy.biz.post.domain.Comment;
import fouragrant.scentasy.biz.post.dto.CommentListResDto;
import fouragrant.scentasy.biz.post.dto.CommentResDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    //comment를 commentListRestDto로 변환
    public CommentListResDto toDto(Comment comment) {
        List<CommentListResDto> childComments = comment.getChildComment().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new CommentListResDto(comment, childComments);
    }

    // Comment 엔티티 리스트를 CommentListResDto 리스트로 변환
    public List<CommentListResDto> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }
}