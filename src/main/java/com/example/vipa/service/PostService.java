package com.example.vipa.service;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Category;
import com.example.vipa.model.Post;
import com.example.vipa.model.PostImage;
import com.example.vipa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String POST_NOT_FOUND_MESSAGE = "Объявление не найдено.";

    private final PostMapper postMapper;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final PostRepository postRepository;

    @Transactional
    public PostDetailsDto getPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(POST_NOT_FOUND_MESSAGE));
        post.setNumberOfViews(post.getNumberOfViews() + 1);
        postRepository.save(post);

        PostDetailsDto postDetailsDto = postMapper.convertToPostDetailsDto(post);

        return postDetailsDto;
    }

    public List<Post> getPostsByIds(List<Integer> listIds) {
        return postRepository.findAllById(listIds);
    }

    public Post getPostEntity(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(POST_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public List<PostPreviewDto> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public List<PostPreviewDto> getPosts(Pageable pageable, String postTitlePattern) {
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
    public List<PostPreviewDto> getMostPopularPosts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC, "numberOfViews"));
        return postRepository.findAll(pageable).stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public PostDetailsDto createPost(int authorId, PostDetailsDto postDetailsDto, MultipartFile file) throws IOException {
        Post postToSave = postMapper.convertToPost(postDetailsDto);
        postToSave.setAuthor(clientService.getClientEntity(authorId));
        postToSave.setCategory(categoryService.getCategoryEntity(postDetailsDto.getCategoryId()));
        postToSave.setStatus("status");
        postToSave.setCreatedAt(LocalDate.now());
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


    @Transactional
    public List<PostPreviewDto> getPostsByCategory(Pageable pageable, int categoryId) {
        Category category = categoryService.getCategoryEntity(categoryId);
        List<Post> listPosts = postRepository.findAllByCategory(category, pageable);
        return listPosts.stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }
}
