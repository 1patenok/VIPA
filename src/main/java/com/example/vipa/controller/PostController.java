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
        log.info("inside getPostPage(), postId: {}", postId);
        PostDetailsDto post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "/post/post-page";
    }

    @GetMapping("/catalog")
    public String getPostCatalogPage(Model model, Pageable pageable,
                                     @RequestParam("postTitlePattern") String postTitlePattern) {
        log.info("inside getPostCatalogPage(), параметры пэйджинга: {}, postTitlePattern: {}",
                pageable, postTitlePattern);
        List<PostPreviewDto> posts = postService.getPostPage(pageable, postTitlePattern);
        model.addAttribute("posts", posts);
        return "/post/post-catalog-page";
    }

    @GetMapping("/new")
    public String getNewPostPage(Model model) {
        log.info("inside getNewPostPage()");
        model.addAttribute("post", new PostDetailsDto());
        return "/post/new-post-page";
    }

    @GetMapping("/edit")
    public String getEditPostPage(Model model) {
        log.info("inside getEditPostPage()");
        model.addAttribute("post", new PostDetailsDto());
        return "/post/edit-post-page";
    }

    @PostMapping("/new/{userId}")
    public String createPost(Model model) {
        log.info("inside createPost()");
        PostDetailsDto postDetailsDto = (PostDetailsDto) model.getAttribute("post");
        log.info("postDetailsDto: {}", postDetailsDto);
        PostDetailsDto createdPost = postService.createPost(postDetailsDto);
        model.addAttribute("post", createdPost);
        return "/post/post-page";
    }

    @PutMapping("/edit/{postId}")
    public String updatePost(Model model, @PathVariable("postId") int postId) {
        log.info("inside updatePost(), postId: {}", postId);
        PostDetailsDto postDetailsDto = (PostDetailsDto) model.getAttribute("post");
        log.info("postDetailsDto: {}", postDetailsDto);
        PostDetailsDto updatedPost = postService.updatePost(postId, postDetailsDto);
        model.addAttribute("post", updatedPost);
        return "/post/edit-post-page";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        log.info("inside deletePost(), postId: {}", postId);
        postService.deletePost(postId);
        return "/common/homepage";
    }
}
