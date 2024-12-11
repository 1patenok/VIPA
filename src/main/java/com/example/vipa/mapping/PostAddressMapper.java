package com.example.vipa.mapping;

import com.example.vipa.dto.PostAddressDto;
import com.example.vipa.model.PostAddress;
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

    public PostAddressDto convertToPostAddressDto(PostAddress postAddress) {
        log.info("inside convertToPostAddressDto()");
        return modelMapper.map(postAddress, PostAddressDto.class);
    }
}
