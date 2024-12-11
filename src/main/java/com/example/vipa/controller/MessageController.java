package com.example.vipa.controller;


import com.example.vipa.dto.MessageDto;
import com.example.vipa.dto.OrderDetailsDto;
import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @ResponseBody
    @PostMapping("/{senderId}/{recipientId}")
    public ResponseEntity<?> sendMessage(Model model, @PathVariable("senderId") int senderId,
                                         @PathVariable("recipientId") int recipientId){
        log.info("Отправили сообщение");
        MessageDto messageDto = (MessageDto) model.getAttribute("message");
        log.info("messageDto: {}", messageDto);
        messageService.createMessage(senderId, recipientId, messageDto);
        return ResponseEntity.ok("Сообщение успешно отправлено.");
    }

    @ResponseBody
    @GetMapping("/chat")
    public ResponseEntity<?> getAllMessages(Model model, @PathVariable("senderId") int senderId,
                                            @PathVariable("recipientId") int recipientId){
        log.info("Вывели сообщения");
        MessageDto messageDto = (MessageDto) model.getAttribute("message");
        log.info("messageDto: {}", messageDto);
        messageService.getAllMessages(senderId, recipientId, messageDto);

        return ResponseEntity.ok("Сообщения успешно выведены.");
    }
}
