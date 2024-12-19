package com.example.vipa.controller;

import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.FavoritesService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping
    public String getFavoritesPage(Model model, @AuthenticationPrincipal Client currentClient) {
        log.info("Принят запрос на получение списка товаров в избранном. currentClient: {}", currentClient);
        List<PostPreviewDto> favorites = favoritesService.getFavorites(currentClient.getId());
        log.info("favorites: {}", favorites);
        model.addAttribute("favorites", favorites);
        return "/favorites/favorites-page";
    }

    @ResponseBody
    @PostMapping(value = "/{postId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String addPostToFavorites(@AuthenticationPrincipal Client currentClient,
                                                @PathVariable("postId") int postId) {
        log.info("Принят запрос на добавление объявления в избранное. currentClient: {}, postId: {}", currentClient, postId);
        favoritesService.addPostToFavorites(currentClient.getId(), postId);
        return "redirect:/post/" + postId;
        //return ResponseEntity.ok("Объявление успешно добавлено в избранное.");
    }

    @DeleteMapping(value = "/{postId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String deletePostFromFavorites(@AuthenticationPrincipal Client currentClient,
                                                     @PathVariable("postId") int postId) {
        log.info("Принят запрос на удаление объявления из избранного. currentClient: {}, postId: {}", currentClient, postId);
        favoritesService.deletePostFromFavorites(currentClient.getId(), postId);
        return "redirect:/favorites";
        //return ResponseEntity.ok("Объявление успешно удалено из избранного.");
    }
}
