package jjan_back_renewal.post.repository;

import jjan_back_renewal.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
