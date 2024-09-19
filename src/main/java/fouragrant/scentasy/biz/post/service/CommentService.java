package fouragrant.scentasy.biz.post.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.post.domain.Comment;
import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.dto.CommentListResDto;
import fouragrant.scentasy.biz.post.dto.CommentReqDto;
import fouragrant.scentasy.biz.post.dto.CommentResDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.mapper.CommentMapper;
import fouragrant.scentasy.biz.post.repository.CommentRepository;
import fouragrant.scentasy.biz.post.repository.PostRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public CommentResDto createComment(Long postId, Long memberId, CommentReqDto commentReqDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get();
        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Comment comment = new Comment(post, member, commentReqDto.getContent());

        commentRepository.save(comment);

        post.incrementViewCount();
        postRepository.save(post);

        return new CommentResDto(comment);
    }


    public CommentResDto createSecondComment(Long postId, Long memberId, Long parentCommentId, CommentReqDto commentReqDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get();
        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        
        // 부모 댓글 없으면 예외
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new CommonException(ErrorCode.COMMENT_NOT_FOUND));

        Comment comment = new Comment(post, member, parentComment, commentReqDto.getContent());

        commentRepository.save(comment);

        post.incrementViewCount();
        postRepository.save(post);

        return new CommentResDto(comment);
    }

    public List<CommentListResDto> getCommentList(Long postId) {
        List<Comment> parentComments = commentRepository.findByPostIdAndParentCommentIsNull(postId);
        return commentMapper.toDtoList(parentComments);
    }

    public CommentResDto modifyComment(Long commentId, Long memberId, CommentReqDto commentReqDto) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new CommonException(ErrorCode.COMMENT_NOT_FOUND);
        }
        Comment comment = optionalComment.get();

        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //회원과 작성자가 일치하지 않음 예외
        if (!Objects.equals(comment.getMember().getId(), member.getId())){
            throw new CommonException(ErrorCode.MEMBER_NOT_SAME);
        }

        comment.updateComment(commentReqDto.getContent());
        commentRepository.save(comment);

        return new CommentResDto(comment);
    }

    public void deleteComment(Long commentId, Long memberId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new CommonException(ErrorCode.COMMENT_NOT_FOUND);
        }
        Comment comment = optionalComment.get();

        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //회원과 작성자가 일치하지 않음 예외
        if (!Objects.equals(comment.getMember().getId(), member.getId())){
            throw new CommonException(ErrorCode.MEMBER_NOT_SAME);
        }
        commentRepository.delete(comment);
    }
}
