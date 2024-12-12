package com.example.vipa.dto;

import com.example.vipa.model.DeliveryMethod;
import com.example.vipa.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class OrderPreviewDto {
    private int id;
    private int price;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
}
