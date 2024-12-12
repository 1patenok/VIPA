package com.example.vipa.controller;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.OrderPreviewDto;
import com.example.vipa.dto.PostAddressDto;
import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.service.OrderService;
import com.example.vipa.service.PostAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/orderInfo/{orderId}")
    public String getOrderPage(@PathVariable("orderId") int orderId,
                               @ModelAttribute("order") OrderDetailsDto orderDetailsDto) {
        log.info("Получен запрос на просмотр информации о заказе. orderId: {}", orderId);
        orderDetailsDto = orderService.getOrder(orderId);
        return "/order/order-page";
    }

    @GetMapping("/orderList/{clientId}")
    public String getOrdersPage(@PathVariable("clientId") int clientId,
                                @ModelAttribute("orderList") List<OrderPreviewDto> orderList) {
        log.info("Получен запрос на просмотр заказов пользователя. clientId: {}", clientId);
        orderList = orderService.getOrders(clientId);
        return "/order/orders-page";
    }

    @GetMapping("/post_addresses")
    public String getPostAddress(Model model) {
        List<PostAddressDto> listPostAddress  = postAddressService.getPostAddress();
        model.addAttribute("addresses", listPostAddress);
        return "/order/orders-page";
    }

    @ResponseBody
    @PostMapping(value = "/{clientId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> placeAnOrder(@PathVariable("clientId") int clientId,
                                          @ModelAttribute("order") OrderDetailsDto orderDetailsDto) {
        log.info("Получен запрос на оформление нового заказа. clientId: {}, orderDetailsDto: {}", clientId, orderDetailsDto);
        orderService.createOrder(clientId, orderDetailsDto);
        return ResponseEntity.ok("Заказ успешно оформлен.");
    }
}
