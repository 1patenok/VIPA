package com.example.vipa.dto;

import com.example.vipa.model.DeliveryMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailsDto {
    private int id;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private String timeOfDelivery;
    private String paymentMethod;
    private String cardNumber;
    private List<Integer> postsInOrder;
}
