package com.example.vipa.controller;


import com.example.vipa.dto.MessageDto;
import com.example.vipa.model.Client;
import com.example.vipa.model.DialogType;
import com.example.vipa.model.Message;
import com.example.vipa.service.DialogService;
import com.example.vipa.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/messages/{dialogId}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping(/*produces = {"application/json; charset=UTF-8"}*/)
    public String sendMessage(@ModelAttribute("message") MessageDto messageDto,
                              @PathVariable("dialogId") int dialogId,
                              @AuthenticationPrincipal Client currentClient) {
        log.info("Принят запрос на отправку сообщения. dialogId: {}, currentClient: {}", dialogId, currentClient);
        messageService.createMessage(dialogId, currentClient.getId(), messageDto);
        return "redirect:/dialogs/" + dialogId;
        //return ResponseEntity.ok("Сообщение успешно отправлено.");
    }

    @PutMapping(value = "/update/{messageId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String updateMessage(@ModelAttribute("message") MessageDto messageDto,
                                @PathVariable("dialogId") int dialogId, @PathVariable("messageId") int messageId) {
        log.info("Принят запрос на изменение сообщения. messageId: {}", messageId);
        messageService.updateMessage(messageId, messageDto);
        return "redirect:/dialogs/" + dialogId;

        //return ResponseEntity.ok("Сообщение успешно изменено.");
    }

    @DeleteMapping(value = "/{messageId}"/*, produces = {"application/json; charset=UTF-8"}*/)
    public String deleteMessage(@PathVariable("dialogId") int dialogId, @PathVariable("messageId") int messageId) {
        log.info("Принят запрос на удаление сообщения. messageId: {}", messageId);
        messageService.deleteMessage(messageId);
        return "redirect:/dialogs/" + dialogId;
        //return ResponseEntity.ok("Сообщение успешно удалено.");
    }
}
