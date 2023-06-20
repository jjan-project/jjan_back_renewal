package jjan_back_renewal.post.service;

import jjan_back_renewal.post.dto.PostCreateRequestDto;
import jjan_back_renewal.post.dto.PostDto;
import jjan_back_renewal.post.entity.PostEntity;
import jjan_back_renewal.post.repository.PostRepository;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public PostDto write(String userEmail, PostCreateRequestDto requestDto) {
        UserEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchEmailException(userEmail));
        PostDto postDto = new PostDto();
        postDto.setAuthor(userEntity);
        postDto.setTitle(requestDto.getTitle());
        postDto.setContent(requestDto.getContent());
        PostEntity save = postRepository.save(postDto.toEntity());
        return new PostDto(save);
    }
}
