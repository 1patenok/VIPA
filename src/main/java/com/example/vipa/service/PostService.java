package com.example.vipa.service;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapper.PostMapper;
import com.example.vipa.model.Post;
import com.example.vipa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostDetailsDto getPost(int postId) {
        return postRepository.findById(postId).map(postMapper::convertToPostDetailsDto)
                .orElseThrow(() -> new RuntimeException("Пост с id=" + postId + " не найден."));
    }

    public Post getPostEntity(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост с id=" + postId + " не найден."));
    }

    public List<PostPreviewDto> getPostPage(Pageable pageable, String postTitlePattern) {
        return postRepository.findAllByTitleLike(postTitlePattern, pageable).stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    public PostDetailsDto createPost(PostDetailsDto postDetailsDto) {
        Post postToSave = postMapper.convertToPost(postDetailsDto);
        return postMapper.convertToPostDetailsDto(postRepository.save(postToSave));
    }

    public void updatePost(Post updatedPost) {
        postRepository.save(updatedPost);
    }

    public PostDetailsDto updatePost(int postId, PostDetailsDto postDetailsDto) {
        Post updatedPost = postMapper.convertToPost(postDetailsDto).setPostId(postId);
        return postMapper.convertToPostDetailsDto(postRepository.save(updatedPost));
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
}
