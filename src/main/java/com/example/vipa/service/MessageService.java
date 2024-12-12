package com.example.vipa.service;

import com.example.vipa.dto.DialogPreviewDto;
import com.example.vipa.dto.MessageDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.DialogMapper;
import com.example.vipa.mapping.MessageMapper;
import com.example.vipa.model.Client;
import com.example.vipa.model.Dialog;
import com.example.vipa.model.Message;
import com.example.vipa.repository.DialogRepository;
import com.example.vipa.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private static final String MESSAGE_NOT_FOUND_MESSAGE = "Сообщение не найдено.";

    private final MessageMapper messageMapper;
    private final ClientService clientService;
    private final DialogService dialogService;
    private final MessageRepository messageRepository;

    public Message getMessageEntity(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND_MESSAGE));
    }
    @Transactional
    public void createMessage(int dialogId, int senderId, MessageDto messageDto) {
        log.info("inside createMessage(), messageDto: {}", messageDto);
        Client sender = clientService.getClientEntity(senderId);
        Message messageToSave = messageMapper.convertToMessage(messageDto);
        messageToSave.setSendDateTime(LocalDateTime.now());
        messageToSave.setSenderName(sender.getFullName());
        messageToSave.setDialog(dialogService.getDialogEntity(dialogId));
        messageRepository.save(messageToSave);
    }

    @Transactional
    public void updateMessage(int messageId, MessageDto messageDto) {
        Message messageToUpdate = getMessageEntity(messageId);
        messageToUpdate.setText(messageDto.getText());
        messageRepository.save(messageToUpdate);
    }

    public void deleteMessage(int messageId) {
        messageRepository.deleteById(messageId);
    }
}
