package com.example.vipa.controller;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart/{clientId}")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCartPage(Model model, @PathVariable("clientId") int clientId) {
        log.info("Принят запрос на получение списка товаров в корзине. clientId: {}", clientId);
        List<PostPreviewDto> postsInCart = cartService.getProductsInCart(clientId);
        log.info("cart: {}", postsInCart);
        model.addAttribute("postsInCart", postsInCart);
        return "/post/cart-page";
    }

    @ResponseBody
    @PostMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> addPostToCart(@PathVariable("clientId") int clientId,
                                           @PathVariable("postId") int postId) {
        log.info("Принят запрос на добавление объявления в корзину. clientId: {}, postId: {}", clientId, postId);
        cartService.addPostToCart(clientId, postId);
        return ResponseEntity.ok("Объявление успешно добавлено в корзину.");
    }

    @ResponseBody
    @DeleteMapping(value = "/{postId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> deletePostFromCart(@PathVariable("clientId") int clientId,
                                                @PathVariable("postId") int postId) {
        log.info("Принят запрос на удаление объявления из корзины. clientId: {}, postId: {}", clientId, postId);
        cartService.deletePostFromCart(clientId, postId);
        return ResponseEntity.ok("Объявление успешно удалено из корзины.");
    }


}

