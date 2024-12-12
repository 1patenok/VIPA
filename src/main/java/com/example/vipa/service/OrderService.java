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

import java.util.List;

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

        if (orderDetailsDto.getDeliveryMethod().equals(DeliveryMethod.COURIER)) {
            orderToSave.setTimeOfDelivery(1);
        } else {
            orderToSave.setTimeOfDelivery(3);
        }
        List<Post> listOfPosts = postService.getPostsByIds(orderDetailsDto.getPostsInOrder());
        int sumOfPrice = 0;
        for (Post post : listOfPosts) {
            sumOfPrice += post.getPrice();
        }
        orderToSave.setPrice(sumOfPrice);
        Client client = clientService.getClientEntity(clientId);
        orderToSave.setClient(client);

        PaymentAccount paymentAccount = paymentAccountService.getPaymentAccountByCardNumber(orderDetailsDto.getCardNumber());
        if (paymentAccount.getCurrentSum() < sumOfPrice) {
            throw new NotEnoughMoneyException("Недостаточно средств на счету.");
        }
        paymentAccount.setCurrentSum(paymentAccount.getCurrentSum() - sumOfPrice);
        paymentAccountService.savePaymentAccount(paymentAccount);
        orderToSave.setOrderStatus(OrderStatus.PROCESSING);
        orderRepository.save(orderToSave);
    }
}
