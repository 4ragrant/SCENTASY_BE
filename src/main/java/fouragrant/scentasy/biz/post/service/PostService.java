package fouragrant.scentasy.biz.post.service;

import fouragrant.scentasy.biz.member.domain.Member;
import fouragrant.scentasy.biz.member.service.MemberService;
import fouragrant.scentasy.biz.perfume.domain.Perfume;
import fouragrant.scentasy.biz.perfume.service.PerfumeService;
import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.domain.PostLike;
import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.mapper.PostMapper;
import fouragrant.scentasy.biz.post.repository.PostLikeRepository;
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
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final MemberService memberService;
    private final PerfumeService perfumeService;
    private final PostLikeRepository postLikeRepository;

    public List<PostResDto> getPostList() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        return posts.stream()
                .map(postMapper::toPostResDto) //엔티티를 dto로 변환
                .collect(Collectors.toList()); // 리스트 변환
    }

    public List<PostResDto> getTopPostList() {
        List<Post> posts = postRepository.findTop3ByOrderByLikeCountDesc();
        if (posts.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        return posts.stream()
                .map(postMapper::toPostResDto) //엔티티를 dto로 변환
                .collect(Collectors.toList()); // 리스트 변환
    }

    public PostResDto createPost(PostReqDto postReqDto, Long memberId, Long perfumeId) {
        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Perfume perfume = perfumeService.findPerfumeById(perfumeId);
        // 향수가 없으면 예외 던짐
        if (perfume == null) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        Post post = new Post(postReqDto, member, perfume);

        postRepository.save(post);

        return postMapper.toPostResDto(post);
    }

    public PostResDto getPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get(); // Optional에서 Post 객체 추출
        // 2. 조회수 증가
        post.incrementViewCount();
        postRepository.save(post);  // 증가된 조회수 저장

        return postMapper.toPostResDto(post);
    }

    public PostResDto modifyPost(Long postId, Long memberId, Long perfumeId, PostReqDto postReqDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get(); // Optional에서 Post 객체 추출

        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //회원과 작성자가 일치하지 않음 예외
        if (!Objects.equals(post.getMember().getId(), member.getId())){
            throw new CommonException(ErrorCode.MEMBER_NOT_SAME);
        }

        Perfume perfume = perfumeService.findPerfumeById(perfumeId);
        // 향수가 없으면 예외 던짐
        if (perfume == null) {
            throw new CommonException(ErrorCode.PERFUME_NOT_FOUND);
        }

        post.updatePost(postReqDto.getTitle(), postReqDto.getContent(),perfume);

        postRepository.save(post);

        return postMapper.toPostResDto(post);
    }

    public void deletePost(Long postId, Long memberId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            // 포스트가 없을 경우
            log.warn(ErrorCode.POST_NOT_FOUND.getMessage());
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get(); // Optional에서 Post 객체 추출

        Member member = memberService.findById(memberId);
        // 회원이 없으면 예외 던짐
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //회원과 작성자가 일치하지 않음 예외
        if (!Objects.equals(post.getMember().getId(), member.getId())){
            throw new CommonException(ErrorCode.MEMBER_NOT_SAME);
        }
        postRepository.delete(post);
    }

    public PostResDto createPostlike(Long postId, Long memberId) {
        // 1. 포스트 조회
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new CommonException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optionalPost.get();

        // 2. 회원 조회
        Member member = memberService.findById(memberId);
        if (member == null) {
            throw new CommonException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 3. 중복 좋아요 확인
        Optional<PostLike> existingLike = postLikeRepository.findByPostAndMember(post, member);
        if (existingLike.isPresent()) {
            throw new CommonException(ErrorCode.POST_LIKE_ALREADY_EXISTS);
        }

        // 4. 좋아요 생성 및 저장
        PostLike postLike = new PostLike(post, member);
        postLikeRepository.save(postLike);

        post.getLikeCount();
        postRepository.save(post);  // 증가된 조회수 저장

        return postMapper.toPostResDto(post);
    }
}
