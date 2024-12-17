package com.example.vipa.repository;

import com.example.vipa.model.Category;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByAuthor(Client author);
    List<Post> findAllByTitleLike(String title, Pageable pageable);
    List<Post> findAllByCategory(Category category, Pageable pageable);
}
