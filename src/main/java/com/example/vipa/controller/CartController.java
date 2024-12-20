package com.example.vipa.controller;

import com.example.vipa.dto.OrderDetailsOutputDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.CartService;
import com.example.vipa.service.DeliveryAddressService;
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
    private final DeliveryAddressService deliveryAddressService;

    @GetMapping
    public String getCartPage(Model model, @AuthenticationPrincipal Client currentClient) {
        log.info("Принят запрос на получение списка товаров в корзине. currentClient: {}", currentClient);
        model.addAttribute("posts", cartService.getProductsInCart(currentClient.getId()));
        model.addAttribute("order", new OrderDetailsOutputDto());
        model.addAttribute("deliveryAddresses", deliveryAddressService.getAddresses());
        return "/cart/cart-page";
    }

    @PostMapping(value = "/{postId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String addPostToCart(@AuthenticationPrincipal Client currentClient,
                                           @PathVariable("postId") int postId) {
        log.info("Принят запрос на добавление объявления в корзину. currentClient: {}, postId: {}", currentClient, postId);
        cartService.addPostToCart(currentClient.getId(), postId);
        return "redirect:/posts/" + postId;
        //return ResponseEntity.ok("Объявление успешно добавлено в корзину.");
    }

    @GetMapping(value = "/delete/{postId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String deletePostFromCart(@AuthenticationPrincipal Client currentClient,
                                                @PathVariable("postId") int postId) {
        log.info("Принят запрос на удаление объявления из корзины. currentClient: {}, postId: {}", currentClient, postId);
        cartService.deletePostFromCart(currentClient.getId(), postId);
        return "redirect:/cart";
        //return ResponseEntity.ok("Объявление успешно удалено из корзины.");
    }


}

