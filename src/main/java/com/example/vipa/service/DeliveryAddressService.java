package com.example.vipa.service;

import com.example.vipa.dto.DeliveryAddressDto;
import com.example.vipa.mapping.DeliveryAddressMapper;
import com.example.vipa.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressMapper deliveryAddressMapper;
    private final DeliveryAddressRepository deliveryAddressRepository;

    public List<DeliveryAddressDto> getAddresses() {
        return deliveryAddressRepository.findAll().stream()
                .map(deliveryAddressMapper::convertToDto)
                .toList();
    }
}
