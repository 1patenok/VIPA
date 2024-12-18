package com.example.vipa.dto;

import com.example.vipa.model.DeliveryMethod;
import com.example.vipa.model.OrderStatus;
import com.example.vipa.model.PaymentMethod;

import java.util.List;

public class OrderDetailsDto {
    private int id;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private List<Integer> postsInOrder;
}
