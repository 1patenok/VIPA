package com.example.vipa.controller;

import com.example.vipa.dto.*;
import com.example.vipa.model.Client;
import com.example.vipa.service.OrderService;
import com.example.vipa.service.PostAddressService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PostAddressService postAddressService;

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
        List<DeliveryAddressDto> deliveryAddresses  = postAddressService.getPostAddress();
        model.addAttribute("addresses", deliveryAddresses);
        return "/order/orders-page";
    }

    @ResponseBody
    @PostMapping(value = "/new", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> placeAnOrder(@AuthenticationPrincipal Client currentClient,
                                          @ModelAttribute("order") OrderDetailsDto orderDetailsDto) {
        log.info("Получен запрос на оформление нового заказа. currentClient: {}, orderDetailsDto: {}", currentClient, orderDetailsDto);
        orderService.createOrder(currentClient.getId(), orderDetailsDto);
        return ResponseEntity.ok("Заказ успешно оформлен.");
    }
}
