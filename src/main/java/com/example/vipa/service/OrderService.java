package com.example.vipa.service;

import com.example.vipa.dto.OrderDetailsInputDto;
import com.example.vipa.dto.OrderDetailsOutputDto;
import com.example.vipa.dto.OrderPreviewDto;
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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String ORDER_NOT_FOUND_MESSAGE = "Заказ не найден.";

    private final OrderMapper orderMapper;
    private final ClientService clientService;
    private final PostService postService;
    private final CartService cartService;
    private final PaymentAccountService paymentAccountService;
    private final OrderRepository orderRepository;
    private final EmailSenderService emailSenderService;

    @Transactional
    public OrderDetailsOutputDto getOrder(int orderId) {
        return orderRepository.findById(orderId).map(orderMapper::convertToOrderDetailsOutputDto)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public List<OrderPreviewDto> getOrders(int clientId) {
        return orderRepository.findAllByClientId(clientId).stream()
                .map(orderMapper::convertToOrderPreviewDto)
                .toList();
    }

    @Transactional
    public OrderDetailsOutputDto createOrder(int clientId, OrderDetailsInputDto orderDetailsInputDto) {
        log.info("inside createOrder(), orderDetailsDto: {}", orderDetailsInputDto);

        Order orderToSave = orderMapper.convertToOrder(orderDetailsInputDto);
        orderToSave.setDeliveryMethod(DeliveryMethod.COURIER);
        orderToSave.setPaymentMethod(PaymentMethod.BY_CARD);
        orderToSave.setDeliveryDate(LocalDate.now().plusDays(3));
        orderToSave.setPostsInOrder(postService.getPostsByIds(orderDetailsInputDto.getPostsInOrder()));
        orderToSave.setPrice(
                orderToSave.getPostsInOrder().stream()
                        .mapToInt(Post::getPrice)
                        .sum());
        orderToSave.setClient(clientService.getClientEntity(clientId));
        orderToSave.setOrderDate(LocalDate.now());
        orderToSave.setStatus(OrderStatus.PROCESSING);
        // совершение платежа
        paymentAccountService.makeAPayment(orderToSave.getPrice(), orderDetailsInputDto.getCardNumber());
        // удаление заказанных товаров из корзины
        orderToSave.getPostsInOrder().forEach(post -> {
            cartService.deletePostFromCart(orderToSave.getClient(), post);
            post.setStatus(PostStatus.INACTIVE);
            postService.updatePost(post);
        });
        OrderDetailsOutputDto savedOrder = orderMapper.convertToOrderDetailsOutputDto(orderRepository.save(orderToSave));
        // отправка email с информацией о заказе
        emailSenderService.sendEmailWithOrderInfo(generateOrderInfo(savedOrder), orderToSave.getClient().getEmail());
        return savedOrder;
    }

    @Transactional
    public String generateOrderInfo(OrderDetailsOutputDto order) {
        StringBuilder orderInfoBuilder = new StringBuilder();
        orderInfoBuilder
                .append("Ваш заказ успешно оформлен!\n")
                .append("Состав заказа:\n");
        // Формирование информации о заказе
        order.getPostsInOrder().stream()
                .filter(Objects::nonNull)
                .forEach(post -> orderInfoBuilder
                        .append(" - Название: ").append(post.getTitle())
                        .append("\nCтоимость: ").append(post.getPrice())
                        .append("\nИмя продавца: ").append(post.getAuthor().getName() + post.getAuthor().getSurname()));
        orderInfoBuilder.append("\n\nИтоговая стоимость заказа: ").append(order.getPrice());
        return orderInfoBuilder.toString();
    }
}
