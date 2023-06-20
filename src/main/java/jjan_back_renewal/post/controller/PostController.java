package jjan_back_renewal.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jjan_back_renewal.join.auth.JwtProvider;
import jjan_back_renewal.post.dto.PostCreateRequestDto;
import jjan_back_renewal.post.dto.PostCreateResponseDto;
import jjan_back_renewal.post.dto.PostDto;
import jjan_back_renewal.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "글쓰기", description = "글씁니다")
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> writePost(@RequestBody PostCreateRequestDto createRequestDto, HttpServletRequest request) {
        String userEmail = jwtProvider.getUserEmail(request);
        PostDto write = postService.write(userEmail, createRequestDto);
        return ResponseEntity.ok().body(new PostCreateResponseDto(write));
    }
}
