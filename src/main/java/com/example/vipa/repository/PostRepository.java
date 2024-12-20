package com.example.vipa.repository;

import com.example.vipa.model.Category;
import com.example.vipa.model.Post;
import com.example.vipa.model.PostStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    //List<Post> findAllByAuthor(Client author);
    List<Post> findAllByTitleLikeIgnoreCaseAndStatus(String title, PostStatus postStatus, Pageable pageable);
    List<Post> findAllByCategoryAndStatus(Category category, PostStatus postStatus, Pageable pageable);
    List<Post> findAllByStatus(PostStatus postStatus, Pageable pageable);
}
