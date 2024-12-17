package com.example.vipa.service;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.OrderPreviewDto;
import com.example.vipa.exception.NotEnoughMoneyException;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.OrderMapper;
import com.example.vipa.model.*;
import com.example.vipa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String ORDER_NOT_FOUND_MESSAGE = "Заказ не найден.";

    private final OrderMapper orderMapper;
    private final ClientService clientService;
    private final PostService postService;
    private final PaymentAccountService paymentAccountService;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDetailsDto getOrder(int orderId) {
        return orderRepository.findById(orderId).map(orderMapper::convertToOrderDetailsDto)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public List<OrderPreviewDto> getOrders(int clientId) {
        return orderRepository.findAllByClientId(clientId).stream()
                .map(orderMapper::convertToOrderPreviewDto)
                .toList();
    }

    @Transactional
    public void createOrder(int clientId, OrderDetailsDto orderDetailsDto) {
        log.info("inside createOrder(), orderDetailsDto: {}", orderDetailsDto);
        Order orderToSave = orderMapper.convertToOrder(orderDetailsDto);
        switch (orderDetailsDto.getDeliveryMethod()) {
            case COURIER -> orderToSave.setTimeOfDelivery(1);
            case DELIVERY_POINT -> orderToSave.setTimeOfDelivery(3);
        }
        orderToSave.setPrice(
                postService.getPostsByIds(orderDetailsDto.getPostsInOrder()).stream()
                        .mapToInt(Post::getPrice)
                        .sum());
        orderToSave.setClient(clientService.getClientEntity(clientId));
        orderToSave.setOrderDate(LocalDate.now());
        PaymentAccount paymentAccount = paymentAccountService.getPaymentAccountByCardNumber(orderDetailsDto.getCardNumber());
        if (paymentAccount.getCurrentSum() < orderToSave.getPrice()) {
            throw new NotEnoughMoneyException("Недостаточно средств на счету.");
        }
        paymentAccount.setCurrentSum(paymentAccount.getCurrentSum() - orderToSave.getPrice());
        paymentAccountService.savePaymentAccount(paymentAccount);
        orderToSave.setOrderStatus(OrderStatus.PROCESSING);
        orderRepository.save(orderToSave);
    }
}
