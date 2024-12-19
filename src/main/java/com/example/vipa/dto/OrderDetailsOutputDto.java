package com.example.vipa.dto;

import com.example.vipa.model.DeliveryMethod;
import com.example.vipa.model.OrderStatus;
import com.example.vipa.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class OrderDetailsOutputDto {
    private int id;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
    private String timeOfDelivery;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private LocalDate date;
    private LocalDate deliveryDate;
    private String cardNumber;
    private List<PostPreviewDto> postsInOrder;
}
