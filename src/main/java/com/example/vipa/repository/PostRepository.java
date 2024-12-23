package com.example.vipa.repository;

import com.example.vipa.model.Post;
import com.example.vipa.model.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    //List<Post> findAllByAuthor(Client author);
    List<Post> findAllByTitleLikeIgnoreCaseAndStatus(String title, PostStatus postStatus, Pageable pageable);
    Page<Post> findAll(Specification specification, Pageable pageable);
    List<Post> findAllByStatus(PostStatus postStatus, Pageable pageable);
}
