package jjan_back_renewal.post.service;

import jjan_back_renewal.post.dto.PostCreateRequestDto;
import jjan_back_renewal.post.dto.PostDto;

public interface PostService {
    PostDto write(String userEmail, PostCreateRequestDto postDto);
}
