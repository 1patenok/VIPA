package com.example.vipa.controller;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public String getPostPage(Model model, @PathVariable("postId") int postId) {
        log.info("Получен запрос на просотр объявления. postId: {}", postId);
        PostDetailsDto post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "/post/post-page";
    }

    @GetMapping("/catalog")
    public String getPostCatalogPage(Model model, Pageable pageable,
                                     @RequestParam("postTitlePattern") String postTitlePattern) {
        log.info("Получен запрос на просмотр каталога объявлений. Параметры пэйджинга: {}, postTitlePattern: {}",
                pageable, postTitlePattern);
        List<PostPreviewDto> posts = postService.getPostPage(pageable, postTitlePattern);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page";
    }

    @GetMapping("/{authorId}/publications")
    public String getPublications(Model model, @PathVariable("authorId") int authorId) {
        log.info("Получен запрос на просмотр объявлений пользователя. authorId: {}", authorId);
        List<PostPreviewDto> posts = postService.getPostsByAuthor(authorId);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page";
    }

    @GetMapping("/new")
    public String getNewPostPage(Model model) {
        log.info("Получен запрос на получение формы для создания нового объявления.");
        model.addAttribute("post", new PostDetailsDto());
        return "/post/new-post-page";
    }

    @GetMapping("/edit")
    public String getEditPostPage(Model model) {
        log.info("Получен запрос на получение формы для редактирования объявления.");
        model.addAttribute("post", new PostDetailsDto());
        return "/post/edit-post-page";
    }

    @PostMapping("/new/{clientId}")
    public String createPost(Model model, @PathVariable("clientId") int authorId) {
        log.info("Получен запрос на публикацию нового объявления.");
        PostDetailsDto postDetailsDto = (PostDetailsDto) model.getAttribute("post");
        log.info("postDetailsDto: {}", postDetailsDto);
        PostDetailsDto createdPost = postService.createPost(authorId, postDetailsDto);
        model.addAttribute("post", createdPost);
        return "/post/post-page";
    }

    @PutMapping("/edit/{postId}")
    public String updatePost(Model model, @PathVariable("postId") int postId) {
        log.info("Получен запрос на обновление информации об объявлении. postId: {}", postId);
        PostDetailsDto postDetailsDto = (PostDetailsDto) model.getAttribute("post");
        log.info("postDetailsDto: {}", postDetailsDto);
        PostDetailsDto updatedPost = postService.updatePost(postId, postDetailsDto);
        model.addAttribute("post", updatedPost);
        return "/post/edit-post-page";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        log.info("Получен запрос на удаление объявления. postId: {}", postId);
        postService.deletePost(postId);
        return "/common/homepage";
    }
}
