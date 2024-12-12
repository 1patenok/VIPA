package com.example.vipa.service;

import com.example.vipa.dto.DialogDetailsDto;
import com.example.vipa.dto.DialogPreviewDto;
import com.example.vipa.dto.MessageDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.DialogMapper;
import com.example.vipa.mapping.MessageMapper;
import com.example.vipa.model.Client;
import com.example.vipa.model.Dialog;
import com.example.vipa.model.Message;
import com.example.vipa.model.Post;
import com.example.vipa.repository.DialogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DialogService {

    private static final String DIALOG_NOT_FOUND_MESSAGE = "Диалог не найден.";

    private final DialogMapper dialogMapper;
    private final PostService postService;
    private final ClientService clientService;
    private final DialogRepository dialogRepository;


    public Dialog getDialogEntity(int dialogId) {
        return dialogRepository.findById(dialogId)
                .orElseThrow(() -> new NotFoundException(DIALOG_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public DialogDetailsDto getDialog(int dialogId) {
        return dialogRepository.findById(dialogId).map(dialogMapper::convertToDialogDetailsDto)
                .orElseThrow(() -> new NotFoundException(DIALOG_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public List<DialogPreviewDto> getSellerDialogs(int clientId) {
        Client client = clientService.getClientEntity(clientId);
        return client.getSellerDialogs().stream()
                .map(dialogMapper::convertToDialogPreviewDto)
                .toList();
    }

    @Transactional
    public List<DialogPreviewDto> getCustomerDialogs(int clientId) {
        Client client = clientService.getClientEntity(clientId);
        return client.getCustomerDialogs().stream()
                .map(dialogMapper::convertToDialogPreviewDto)
                .toList();
    }

    @Transactional
    public DialogDetailsDto createDialog(int clientId, int postId) {
        Client customer = clientService.getClientEntity(clientId);
        Post post = postService.getPostEntity(postId);
        Dialog newDialog = new Dialog()
                .setCustomer(customer)
                .setPost(post)
                .setMessages(Collections.emptyList());
        return dialogMapper.convertToDialogDetailsDto(dialogRepository.save(newDialog));
    }
}
