package com.example.vipa.controller;


import com.example.vipa.dto.MessageDto;
import com.example.vipa.model.DialogType;
import com.example.vipa.service.DialogService;
import com.example.vipa.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/messages/{dialogId}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @ResponseBody
    @PostMapping(value = "/{senderId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> sendMessage(@ModelAttribute("message") MessageDto messageDto,
                                         @PathVariable("dialogId") int dialogId,
                                         @PathVariable("senderId") int senderId) {
        log.info("Принят запрос на отправку сообщения. dialogId: {}, senderId: {}", dialogId, senderId);
        messageService.createMessage(dialogId, senderId, messageDto);
        return ResponseEntity.ok("Сообщение успешно отправлено.");
    }

    @ResponseBody
    @PutMapping(value = "/update/{messageId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> updateMessage(Model model, @PathVariable("messageId") int messageId) {
        log.info("Принят запрос на изменение сообщения. messageId: {}", messageId);
        MessageDto messageDto = (MessageDto) model.getAttribute("message");
        messageService.updateMessage(messageId, messageDto);
        return ResponseEntity.ok("Сообщение успешно изменено.");
    }

    @ResponseBody
    @DeleteMapping(value = "/{messageId}", produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> deleteMessage(@PathVariable("messageId") int messageId) {
        log.info("Принят запрос на удаление сообщения. messageId: {}", messageId);
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok("Сообщение успешно удалено.");
    }
}
