package jjan_back_renewal.post.dto;

import jjan_back_renewal.post.entity.PostEntity;
import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private UserEntity author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDto(PostEntity postEntity) {
        this.id = postEntity.getId();
        this.author = postEntity.getAuthor();
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
        this.createdAt = postEntity.getCreatedAt();
        this.updatedAt = postEntity.getUpdatedAt();
    }

    public PostEntity toEntity() {
        return PostEntity.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
    }
}
