package com.example.vipa.mapping;

import com.example.vipa.dto.DeliveryAddressDto;
import com.example.vipa.model.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryAddressMapper {

    private final ModelMapper modelMapper;

    public DeliveryAddressDto convertToDto(DeliveryAddress address) {
        return modelMapper.map(address, DeliveryAddressDto.class);
    }
}
