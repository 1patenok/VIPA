package com.example.vipa.mapping;

import com.example.vipa.dto.client.ClientDetailsDto;
import com.example.vipa.dto.client.ClientPreviewDto;
import com.example.vipa.model.Client;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final ModelMapper modelMapper;

    public Client convertToClient(ClientDetailsDto dto) {
        return modelMapper.map(dto, Client.class);
    }

    public ClientPreviewDto convertToClientPreviewDto(Client client) {
        return modelMapper.map(client, ClientPreviewDto.class);
    }
    public ClientDetailsDto convertToClientDetailsDto(Client client) {
        return modelMapper.map(client, ClientDetailsDto.class);
    }
}
