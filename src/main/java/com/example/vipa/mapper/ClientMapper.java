package com.example.vipa.mapper;

import com.example.vipa.dto.NewClientDto;
import com.example.vipa.model.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final ModelMapper modelMapper;

    public Client convertToClient(NewClientDto dto) {
        return modelMapper.map(dto, Client.class);
    }
}
