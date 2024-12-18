package com.example.vipa.mapping;

import com.example.vipa.dto.*;
import com.example.vipa.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order convertToOrder(OrderDetailsOtputDto dto) {
        log.info("inside convertToPost()");
        return modelMapper.map(dto, Order.class);
    }

    public OrderPreviewDto convertToOrderPreviewDto(Order order) {
        log.info("inside convertToPostPreviewDto()");
        return modelMapper.map(order, OrderPreviewDto.class);
    }

    public OrderDetailsOtputDto convertToOrderDetailsDto(Order order) {
        log.info("inside convertToPostDetailsDto()");
        return modelMapper.map(order, OrderDetailsOtputDto.class);
    }


}
