package com.example.vipa.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailsInputDto {
    private int id;
    private String deliveryAddress;
    //private DeliveryMethod deliveryMethod;
    //private PaymentMethod paymentMethod;
    private String cardNumber;
    private List<Integer> postsInOrder;
}
