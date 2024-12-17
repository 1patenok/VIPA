package com.example.vipa.controller;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.CategoryService;
import com.example.vipa.service.DialogService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final DialogService dialogService;
    private final CategoryService categoryService;

    @GetMapping("/{postId}")
    public String getPostPage(Model model, @PathVariable("postId") int postId,
                              @AuthenticationPrincipal Client currentClient) {
        log.info("Получен запрос на просотр объявления. postId: {}", postId);
        PostDetailsDto post = postService.getPost(postId);
        int dialogId = dialogService.getDialogIdByPostAndCustomer(postId, currentClient.getId());
        model.addAttribute("post", post);
        model.addAttribute("dialogId", dialogId);
        return "/post/post-page";
    }

    @GetMapping("/catalog")
    public String getPosts(Model model, @PageableDefault(size = 30, sort = "id") Pageable pageable) {
        log.info("Получен запрос на просмотр каталога объявлений. postTitlePattern: {}", pageable);
        List<PostPreviewDto> posts = postService.getPosts(pageable);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page.html";
    }

    @GetMapping("/catalog/search")
    public String getPostsByTitle(Model model, @PageableDefault(size = 30, sort = "post_id") Pageable pageable,
                                  @RequestParam("postTitlePattern") String postTitlePattern) {
        log.info("Получен запрос на просмотр каталога объявлений. Параметры пэйджинга: {}, postTitlePattern: {}",
                pageable, postTitlePattern);
        List<PostPreviewDto> posts = postService.getPosts(pageable, postTitlePattern);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page.html";
    }

    @GetMapping("/catalog/{categoryId}")
    public String getPostsByCategory(Model model, Pageable pageable, @PathVariable("categoryId") int categoryId){
        log.info("Получен запрос на просмотр каталога объявлений по категории. categoryId: {}", categoryId);
        List<PostPreviewDto> posts = postService.getPostsByCategory(pageable, categoryId);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page.html";
    }

/*    @GetMapping("/{authorId}/publications")
    public String getPublications(Model model, @PathVariable("authorId") int authorId) {
        log.info("Получен запрос на просмотр объявлений пользователя. authorId: {}", authorId);
        List<PostPreviewDto> posts = postService.getPostsByAuthor(authorId);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page.html";
    }*/

    @GetMapping("/new")
    public String getNewPostPage(Model model) {
        log.info("Получен запрос на получение формы для создания нового объявления.");
        model.addAttribute("post", new PostDetailsDto());
        model.addAttribute("categories", categoryService.getCategories());
        return "/post/post-form-page";
    }

    @GetMapping("/edit")
    public String getEditPostPage(Model model) {
        log.info("Получен запрос на получение формы для редактирования объявления.");
        model.addAttribute("post", new PostDetailsDto());
        return "/post/edit-post-page";
    }

    @PostMapping("/new")
    public String createPost(Model model, @AuthenticationPrincipal Client currentClient,
                             @ModelAttribute("post") PostDetailsDto postDetailsDto) {
        log.info("Получен запрос на публикацию нового объявления. currentClient: {}, postDetailsDto: {}",
                currentClient, postDetailsDto);
        PostDetailsDto createdPost = postService.createPost(currentClient.getId(), postDetailsDto);
        model.addAttribute("post", createdPost);
        return "/post/post-page";
    }

    @PutMapping("/edit/{postId}")
    public String updatePost(Model model, @PathVariable("postId") int postId,
                             @ModelAttribute("post") PostDetailsDto postDetailsDto) {
        log.info("Получен запрос на обновление информации об объявлении. postId: {}, postDetailsDto: {}", postId, postDetailsDto);
        PostDetailsDto updatedPost = postService.updatePost(postId, postDetailsDto);
        model.addAttribute("post", updatedPost);
        return "/post/post-page";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        log.info("Получен запрос на удаление объявления. postId: {}", postId);
        postService.deletePost(postId);
        return "/common/homepage-client";
    }


}
