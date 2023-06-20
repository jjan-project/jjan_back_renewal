package jjan_back_renewal.post.dto;

import jjan_back_renewal.config.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateResponseDto extends GenericResponse {
    private PostDto post;
}
