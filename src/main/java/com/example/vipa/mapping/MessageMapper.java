package com.example.vipa.mapping;

import com.example.vipa.dto.MessageDto;
import com.example.vipa.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageMapper {
    private final ModelMapper modelMapper;

    public MessageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Message convertToMessage(MessageDto dto) {
        log.info("inside convertToMessage(), dto: {}", dto);
        return modelMapper.map(dto, Message.class);
    }

    public MessageDto convertToMessageDto(Message message) {
        log.info("inside convertToMessageDto()");
        return modelMapper.map(message, MessageDto.class);
    }
}
