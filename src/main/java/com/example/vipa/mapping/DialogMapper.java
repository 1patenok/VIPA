package com.example.vipa.mapping;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.example.vipa.dto.ClientPreviewDto;
import com.example.vipa.dto.DialogDetailsDto;
import com.example.vipa.dto.DialogPreviewDto;
import com.example.vipa.dto.MessageDto;
import com.example.vipa.model.Client;
import com.example.vipa.model.Dialog;
import com.example.vipa.model.Message;
import com.example.vipa.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DialogMapper {
    private final ModelMapper modelMapper;

    public DialogMapper(ModelMapper modelMapper, ClientMapper clientMapper, MessageMapper messageMapper) {
        this.modelMapper = modelMapper;
        Converter<Post, String> postToPostTitleConverter =
                src -> src.getSource().getTitle();
        Converter<Client, ClientPreviewDto> clientToClientPreviewDtoConverter =
                src -> clientMapper.convertToClientPreviewDto(src.getSource());
        Converter<Post, ClientPreviewDto> postToClientPreviewDtoConverter =
                src -> clientMapper.convertToClientPreviewDto(src.getSource().getAuthor());
        Converter<List<Message>, List<MessageDto>> messageListToMessageDtoListConverter =
                src -> src.getSource().stream()
                        .map(messageMapper::convertToMessageDto)
                        .toList();
        modelMapper.createTypeMap(Dialog.class, DialogDetailsDto.class)
                .addMappings(mapper -> mapper.using(postToPostTitleConverter)
                        .map(Dialog::getPost, DialogDetailsDto::setPostTitle))
                .addMappings(mapper -> mapper.using(postToClientPreviewDtoConverter)
                        .map(Dialog::getPost, DialogDetailsDto::setSeller))
                /*.addMappings(mapper -> mapper.using(clientToClientPreviewDtoConverter)
                        .map(Dialog::getCustomer, DialogDetailsDto::setCustomer))*/
                .addMappings(mapper -> mapper.using(messageListToMessageDtoListConverter)
                        .map(Dialog::getMessages, DialogDetailsDto::setMessages));
                modelMapper.createTypeMap(Dialog.class, DialogPreviewDto.class)
                        .addMappings(mapper -> mapper.using(postToPostTitleConverter)
                                .map(Dialog::getPost, DialogPreviewDto::setPostTitle));
    }

    public DialogPreviewDto convertToDialogPreviewDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogPreviewDto.class);
    }

    public DialogDetailsDto convertToDialogDetailsDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogDetailsDto.class);
    }
}
