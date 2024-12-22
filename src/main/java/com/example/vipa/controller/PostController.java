package com.example.vipa.controller;

import com.example.vipa.dto.post.PostDetailsInputDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

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
        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("dialogId", dialogService.getDialogIdByPostAndCustomer(postId, currentClient.getId()));
        //model.addAttribute("alreadyInCart", favoritesService.isPostInFavorites(currentClient.getId(), postId));
        //model.addAttribute("alreadyInFavorites", cartService.isPostInCart(currentClient.getId(), postId));
        return "/post/post-page";
    }

    @GetMapping("/search")
    public String getPostsPage(Model model,
                               @PageableDefault(sort = "numberOfViews", direction = DESC) Pageable pageable,
                               @RequestParam(value = "search", required = false) String search) {
        log.info("Получен запрос на просмотр объявлений по фильтрам. pageable: {}, search: {}", pageable, search);
        model.addAttribute("posts", postService.getPostsBySearchFiltersAndPageable(search, pageable));
        return "/post/posts-page";
    }

/*    @GetMapping("/catalog/{categoryId}")
    public String getPostsByCategory(Model model, @PageableDefault(sort = "numberOfViews", direction = DESC) Pageable pageable,
                                     @PathVariable("categoryId") int categoryId) {
        log.info("Получен запрос на просмотр каталога объявлений по категории. categoryId: {}", categoryId);
        List<PostPreviewDto> posts = postService.getPostsByCategory(pageable, categoryId);
        log.info("posts: {}", posts);
        model.addAttribute("posts", posts);
        return "/post/posts-page";
    }*/

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
        model.addAttribute("post", new PostDetailsInputDto());
        model.addAttribute("categories", categoryService.getCategories());
        return "/post/post-form-page";
    }

    @GetMapping("/{postId}/edit")
    public String getEditPostPage(Model model, @PathVariable("postId") int postId) {
        log.info("Получен запрос на получение формы для редактирования объявления. postId: {}", postId);
        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("categories", categoryService.getCategories());
        return "/post/post-form-page";
    }

    @PostMapping("/new")
    public String createPost(Model model, @AuthenticationPrincipal Client currentClient,
                             @Valid @ModelAttribute("post") PostDetailsInputDto postDetailsInputDto,
                             BindingResult bindingResult) {
        log.info("Получен запрос на публикацию нового объявления. currentClient: {}, postDetailsDto: {}",
                currentClient, postDetailsInputDto);
        if (bindingResult.hasErrors()) {
            log.error("Ошибка валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("categories", categoryService.getCategories());
            return "/post/post-form-page";
        }
        model.addAttribute("post", postService.createPost(currentClient.getId(), postDetailsInputDto));
        return "/post/post-page";
    }

    @PutMapping("/{postId}/update")
    public String updatePost(Model model, @PathVariable("postId") int postId,
                             @Valid @ModelAttribute("post") PostDetailsInputDto postDetailsInputDto,
                             BindingResult bindingResult) {
        log.info("Получен запрос на обновление информации об объявлении. postId: {}, postDetailsDto: {}", postId, postDetailsInputDto);
        if (bindingResult.hasErrors()) {
            log.error("Ошибка валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("categories", categoryService.getCategories());
            return "/post/post-form-page";
        }
        model.addAttribute("post", postService.updatePost(postId, postDetailsInputDto));
        return "/post/post-page";
    }

    @DeleteMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") int postId) {
        log.info("Получен запрос на удаление объявления. postId: {}", postId);
        postService.deletePost(postId);
        return "/common/homepage-client";
    }


}
