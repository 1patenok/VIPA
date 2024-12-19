package com.example.vipa.mapping;

import com.example.vipa.dto.*;
import com.example.vipa.model.Client;
import com.example.vipa.model.Dialog;
import com.example.vipa.model.Message;
import com.example.vipa.model.Post;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DialogMapper {
    private final ModelMapper modelMapper;

    public DialogMapper(ModelMapper modelMapper, ClientMapper clientMapper, MessageMapper messageMapper) {
        this.modelMapper = modelMapper;
        Converter<Client, ClientPreviewDto> clientToClientPreviewDtoConverter =
                src -> clientMapper.convertToClientPreviewDto(src.getSource());
        Converter<Post, ClientPreviewDto> postToClientPreviewDtoConverter =
                src -> clientMapper.convertToClientPreviewDto(src.getSource().getAuthor());
        Converter<List<Message>, List<MessageDto>> messageListToMessageDtoListConverter =
                src -> src.getSource().stream()
                        .map(messageMapper::convertToMessageDto)
                        .toList();
        modelMapper.createTypeMap(Dialog.class, DialogDetailsDto.class)
/*                .addMappings(mapper -> mapper.using(postToPostTitleConverter)
                        .map(Dialog::getPost, DialogDetailsDto::setPost))*/
                .addMappings(mapper -> mapper.using(postToClientPreviewDtoConverter)
                        .map(Dialog::getPost, DialogDetailsDto::setSeller))
                /*.addMappings(mapper -> mapper.using(clientToClientPreviewDtoConverter)
                        .map(Dialog::getCustomer, DialogDetailsDto::setCustomer))*/
                .addMappings(mapper -> mapper.using(messageListToMessageDtoListConverter)
                        .map(Dialog::getMessages, DialogDetailsDto::setMessages));
    }

    public DialogPreviewDto convertToDialogPreviewDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogPreviewDto.class);
    }

    public DialogDetailsDto convertToDialogDetailsDto(Dialog dialog) {
        return modelMapper.map(dialog, DialogDetailsDto.class);
    }
}
