package com.example.vipa.controller;

import com.example.vipa.dto.order.DeliveryAddressDto;
import com.example.vipa.dto.order.OrderDetailsInputDto;
import com.example.vipa.dto.order.OrderDetailsOutputDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.CartService;
import com.example.vipa.service.DeliveryAddressService;
import com.example.vipa.service.OrderService;
import com.example.vipa.service.PostAddressService;
import com.example.vipa.validation.OrderValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final DeliveryAddressService deliveryAddressService;
    private final PostAddressService postAddressService;
    private final OrderValidator orderValidator;

    @GetMapping("/info/{orderId}")
    public String getOrderPage(Model model, @PathVariable("orderId") int orderId) {
        log.info("Получен запрос на просмотр информации о заказе. orderId: {}", orderId);
        model.addAttribute("order", orderService.getOrder(orderId));
        return "/order/order-page";
    }

    @GetMapping("/list")
    public String getOrdersPage(Model model, @AuthenticationPrincipal Client currentClient) {
        log.info("Получен запрос на просмотр заказов пользователя. currentClient: {}", currentClient);
        model.addAttribute("orders", orderService.getOrders(currentClient.getId()));
        return "/order/orders-page";
    }

    @GetMapping("/delivery-addresses")
    public String getPostAddresses(Model model) {
        List<DeliveryAddressDto> deliveryAddresses = postAddressService.getPostAddress();
        model.addAttribute("addresses", deliveryAddresses);
        return "/order/orders-page";
    }

    @PostMapping(value = "/new"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String placeAnOrder(Model model, @AuthenticationPrincipal Client currentClient,
                               @Valid @ModelAttribute("order") OrderDetailsInputDto orderDetailsInputDto,
                               BindingResult bindingResult) {
        log.info("Получен запрос на оформление нового заказа. currentClient: {}, orderDetailsDto: {}", currentClient, orderDetailsInputDto);
        orderValidator.validate(orderDetailsInputDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Ошибка валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("posts", cartService.getProductsInCart(currentClient.getId()));
            model.addAttribute("order", orderDetailsInputDto);
            model.addAttribute("deliveryAddresses", deliveryAddressService.getAddresses());
            return "/cart/cart-page";
        }
        OrderDetailsOutputDto createdOrder = orderService.createOrder(currentClient.getId(), orderDetailsInputDto);
        return "redirect:/orders/info/" + createdOrder.getId();
        //return ResponseEntity.ok("Заказ успешно оформлен.");
    }
}
