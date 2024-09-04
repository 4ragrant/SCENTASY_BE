package fouragrant.scentasy.biz.post.service;

import fouragrant.scentasy.biz.post.domain.Post;
import fouragrant.scentasy.biz.post.dto.PostReqDto;
import fouragrant.scentasy.biz.post.dto.PostResDto;
import fouragrant.scentasy.biz.post.mapper.PostMapper;
import fouragrant.scentasy.biz.post.repository.PostRepository;
import fouragrant.scentasy.common.exception.CommonException;
import fouragrant.scentasy.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

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

}
