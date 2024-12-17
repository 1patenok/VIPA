package com.example.vipa.controller;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.CartService;
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
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCartPage(Model model, @AuthenticationPrincipal Client currentClient) {
        log.info("Принят запрос на получение списка товаров в корзине. currentClient: {}", currentClient);
        List<PostPreviewDto> postsInCart = cartService.getProductsInCart(currentClient.getId());
        log.info("cart: {}", postsInCart);
        model.addAttribute("posts", postsInCart);
        model.addAttribute("order", new OrderDetailsDto());
        return "/cart/cart-page";
    }

    @ResponseBody
    @PostMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> addPostToCart(@AuthenticationPrincipal Client currentClient,
                                           @PathVariable("postId") int postId) {
        log.info("Принят запрос на добавление объявления в корзину. currentClient: {}, postId: {}", currentClient, postId);
        cartService.addPostToCart(currentClient.getId(), postId);
        return ResponseEntity.ok("Объявление успешно добавлено в корзину.");
    }

    @ResponseBody
    @DeleteMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> deletePostFromCart(@AuthenticationPrincipal Client currentClient,
                                                @PathVariable("postId") int postId) {
        log.info("Принят запрос на удаление объявления из корзины. currentClient: {}, postId: {}", currentClient, postId);
        cartService.deletePostFromCart(currentClient.getId(), postId);
        return ResponseEntity.ok("Объявление успешно удалено из корзины.");
    }


}

