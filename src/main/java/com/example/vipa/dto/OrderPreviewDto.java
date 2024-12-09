package com.example.vipa.dto;

import com.example.vipa.model.DeliveryMethod;
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
    private String deliveryAddress;
    private DeliveryMethod deliveryMethod;
}
