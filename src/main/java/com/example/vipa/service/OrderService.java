package com.example.vipa.service;

import com.example.vipa.dto.OrderDetailsOtputDto;
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
    private final CartService cartService;
    private final EmailSenderService emailSenderService;

    @Transactional
    public OrderDetailsOtputDto getOrder(int orderId) {
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
    public void createOrder(int clientId, OrderDetailsOtputDto orderDetailsDto) {
        log.info("inside createOrder(), orderDetailsDto: {}", orderDetailsDto);
        orderDetailsDto= new OrderDetailsOtputDto().setPostsInOrder(List.of(1, 2, 3))
                .setDeliveryAddress("sdffd").setCardNumber("1234567812345678")
                .setDeliveryMethod(DeliveryMethod.DELIVERY_POINT)
                .setPaymentMethod(PaymentMethod.BY_CARD);

        Order orderToSave = orderMapper.convertToOrder(orderDetailsDto);
        switch (orderDetailsDto.getDeliveryMethod()) {
            case COURIER -> orderToSave.setTimeOfDelivery(1);
            case DELIVERY_POINT -> orderToSave.setTimeOfDelivery(3);
        }
        orderToSave.setPostsInOrder(postService.getPostsByIds(orderDetailsDto.getPostsInOrder()));
        orderToSave.setPrice(
                orderToSave.getPostsInOrder().stream()
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

        Order order = orderRepository.save(orderToSave);
        placeAnOrder(order.getClient(), order.getPostsInOrder());
    }
    @Transactional
    public void placeAnOrder(Client client, List<Post> postsInOrder) {
//        cartValidator.performOrderValidation(cartElements);

        emailSenderService.sendEmailWithOrderInfo(generateOrderInfo(postsInOrder), client.getEmail());

        postsInOrder
                .forEach(post -> {
                    client.removeFromCart(post);
                    clientService.updateClient(client);
                });
    }

    @Transactional
    public String generateOrderInfo(List<Post> postsInOrder) {
        StringBuilder orderInfo = new StringBuilder();
        orderInfo
                .append("Ваш заказ успешно оформлен!\n")
                .append("Состав заказа:\n");
        // Формирование информации о заказе
        postsInOrder.stream()
                .filter(post -> post != null)
                .forEach(post -> orderInfo
                        .append(" - Название: " + post.getTitle() + "\n")
                        .append("Cтоимость: " + post.getPrice() + "\n")
                        .append("Имя продавца: " + post.getAuthor() + "\n")
                );

// Расчет итоговой стоимости
        double orderPrice = postsInOrder.stream()
                .filter(post -> post != null)
                .mapToDouble(Post::getPrice)
                .sum();

        orderInfo.append("\nИтоговая стоимость заказа: ").append(orderPrice);

        return orderInfo.toString();
    }
}
