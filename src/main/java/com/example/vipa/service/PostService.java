package com.example.vipa.service;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Post;
import com.example.vipa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String POST_NOT_FOUND_MESSAGE = "Объявление не найдено.";

    private final PostMapper postMapper;
    private final ClientService clientService;
    private final PostRepository postRepository;

    @Transactional
    public PostDetailsDto getPost(int postId) {
        return postRepository.findById(postId).map(postMapper::convertToPostDetailsDto)
                .orElseThrow(() -> new RuntimeException(POST_NOT_FOUND_MESSAGE));
    }

    public List<Post> getPostsByIds(List<Integer> listIds) {
        return postRepository.findAllById(listIds);
    }

    public Post getPostEntity(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(POST_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public List<PostPreviewDto> getPostPage(Pageable pageable, String postTitlePattern) {
        return postRepository.findAllByTitleLike("%" + postTitlePattern + "%", pageable).stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public List<PostPreviewDto> getPostsByAuthor(int authorId) {
        return clientService.getClientEntity(authorId).getPosts().stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public PostDetailsDto createPost(int authorId, PostDetailsDto postDetailsDto) {
        Post postToSave = postMapper.convertToPost(postDetailsDto);
        postToSave.setAuthor(clientService.getClientEntity(authorId));
        log.info("postToSave: {}", postToSave);
        return postMapper.convertToPostDetailsDto(postRepository.save(postToSave));
    }

    public PostDetailsDto updatePost(int postId, PostDetailsDto postDetailsDto) {
        Post updatedPost = postMapper.convertToPost(postDetailsDto).setId(postId);
        return postMapper.convertToPostDetailsDto(postRepository.save(updatedPost));
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
}
