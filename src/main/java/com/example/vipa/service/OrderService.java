package com.example.vipa.service;

import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.OrderPreviewDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.OrderMapper;
import com.example.vipa.model.Client;
import com.example.vipa.model.Order;
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
        Order orderToSave = orderMapper.convertToOrder(orderDetailsDto);
        Client client = clientService.getClientEntity(clientId);
        orderToSave.setClient(client);
        orderRepository.save(orderToSave);
    }
}
