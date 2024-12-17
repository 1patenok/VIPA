package com.example.vipa.controller;

import com.example.vipa.service.CategoryService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/homepage-guest")
    public String getGuestHomepage() {
        log.info("Получен запрос на просмотр гостевой домашней страницы.");
        return "/common/homepage-guest";
    }

    @GetMapping("/homepage-client")
    public String getClientHomepage(Model model, @RequestParam(value = "page", defaultValue = "0") int pageNumber) {
        log.info("Получен запрос на просмотр клиентской домашней страницы.");
        model.addAttribute("posts", postService.getMostPopularPosts(pageNumber));
        model.addAttribute("categories", categoryService.getCategories());
        return "/common/homepage-client";
    }
}
