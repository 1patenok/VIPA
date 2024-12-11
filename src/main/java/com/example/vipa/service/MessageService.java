package com.example.vipa.service;

import com.example.vipa.dto.MessageDto;
import com.example.vipa.mapping.MessageMapper;
import com.example.vipa.model.Message;
import com.example.vipa.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService{
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ClientService clientService;

    @Transactional
    public void createMessage(int senderId, int recipientId, MessageDto messageDto){
        Message message = messageMapper.convertToMessage(messageDto);
        message.setSender(clientService.getClientEntity(senderId));
        message.setRecipient(clientService.getClientEntity(recipientId));
        message.setSendDateTime(LocalDateTime.now());

        messageRepository.save(message);
    }

    @Transactional
    public List<Message> getAllMessages(int senderId, int recipientId){
        List<Message> messagesList = new ArrayList<>();


        Message message = messageMapper.convertToMessage(messageDto);
        message.setSender(clientService.getClientEntity(senderId));
        message.setRecipient(clientService.getClientEntity(recipientId));
        message.setSendDateTime(LocalDateTime.now());

        messagesList.add(message);

        return messagesList;
    }
}
