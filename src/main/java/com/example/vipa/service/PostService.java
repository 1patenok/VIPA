package com.example.vipa.service;

import com.example.vipa.dto.PostDetailsInputDto;
import com.example.vipa.dto.PostDetailsOutputDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Category;
import com.example.vipa.model.Post;
import com.example.vipa.model.PostImage;
import com.example.vipa.model.PostStatus;
import com.example.vipa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String POST_NOT_FOUND_MESSAGE = "Объявление не найдено.";

    private final PostMapper postMapper;
    private final ImageService imageService;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final PostRepository postRepository;

    @Transactional
    public PostDetailsOutputDto getPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(POST_NOT_FOUND_MESSAGE));
        post.setNumberOfViews(post.getNumberOfViews() + 1);
        postRepository.save(post);
        return postMapper.convertToPostDetailsOutputDto(post);
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
        log.info("inside getPosts(), pageagle: {}, postTitlePattern: {}", pageable, postTitlePattern);
        List<Post> result = postRepository.findAllByTitleLikeIgnoreCaseAndStatus("%" + postTitlePattern + "%", PostStatus.ACTIVE, pageable);
        log.info("before stream");
        return result.stream()
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
    public List<PostPreviewDto> getPostsByCategory(Pageable pageable, int categoryId) {
        Category category = categoryService.getCategoryEntity(categoryId);
        List<Post> listPosts = postRepository.findAllByCategoryAndStatus(category, PostStatus.ACTIVE, pageable);
        return listPosts.stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public List<PostPreviewDto> getMostPopularPosts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6, Sort.by(Sort.Direction.DESC, "numberOfViews"));
        return postRepository.findAll(pageable).stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public PostDetailsOutputDto createPost(int authorId, PostDetailsInputDto postDetailsInputDto) {
        Post postToSave = postMapper.convertToPost(postDetailsInputDto);
        postToSave.setAuthor(clientService.getClientEntity(authorId));
        postToSave.setCategory(categoryService.getCategoryEntity(postDetailsInputDto.getCategoryId()));
        postToSave.setStatus(PostStatus.ACTIVE);
        postToSave.setCreatedAt(LocalDate.now());
        postToSave.setImages(
                postDetailsInputDto.getImages().stream()
                        .map(multipart -> new PostImage()
                                .setUrl(imageService.savePostImage(multipart))
                                .setPost(postToSave))
                        .toList());
        log.info("postToSave: {}", postToSave);
        return postMapper.convertToPostDetailsOutputDto(postRepository.save(postToSave));
    }

    public PostDetailsInputDto updatePost(int postId, PostDetailsInputDto postDetailsInputDto) {
        Post updatedPost = postMapper.convertToPost(postDetailsInputDto).setId(postId);
        return postMapper.convertToPostDetailsInputDto(postRepository.save(updatedPost));
    }

    public void updatePost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

}
