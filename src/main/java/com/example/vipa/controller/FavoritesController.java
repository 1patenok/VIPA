package com.example.vipa.controller;

import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.service.FavoritesService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/favorites/{clientId}")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping
    public String getFavoritesPage(Model model, @PathVariable("clientId") int clientId) {
        log.info("Принят запрос на получение списка товаров в избранном. clientId: {}", clientId);
        List<PostPreviewDto> favorites = favoritesService.getFavorites(clientId);
        log.info("favorites: {}", favorites);
        model.addAttribute("favorites", favorites);
        return "/post/favorites-page";
    }

    @ResponseBody
    @PostMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> addPostToFavorites(@PathVariable("clientId") int clientId,
                                             @PathVariable("postId") int postId) {
        log.info("Принят запрос на добавление объявления в избранное. clientId: {}, postId: {}", clientId, postId);
        favoritesService.addPostToFavorites(clientId, postId);
        return ResponseEntity.ok("Объявление успешно добавлено в избранное.");
    }

    @ResponseBody
    @DeleteMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> deletePostFromFavorites(@PathVariable("clientId") int clientId,
                                          @PathVariable("postId") int postId) {
        log.info("Принят запрос на удаление объявления из избранного. clientId: {}, postId: {}", clientId, postId);
        favoritesService.deletePostFromFavorites(clientId, postId);
        return ResponseEntity.ok("Объявление успешно удалено из избранного.");
    }
}
