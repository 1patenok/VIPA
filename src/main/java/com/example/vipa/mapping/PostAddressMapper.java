package com.example.vipa.mapping;

import com.example.vipa.dto.DeliveryAddressDto;
import com.example.vipa.model.DeliveryAddress;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostAddressMapper {
    private final ModelMapper modelMapper;


    public PostAddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeliveryAddressDto convertToPostAddressDto(DeliveryAddress deliveryAddress) {
        log.info("inside convertToPostAddressDto()");
        return modelMapper.map(deliveryAddress, DeliveryAddressDto.class);
    }
}
