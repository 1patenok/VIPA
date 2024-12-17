package com.example.vipa.controller;

import com.example.vipa.dto.DialogDetailsDto;
import com.example.vipa.dto.DialogPreviewDto;
import com.example.vipa.dto.MessageDto;
import com.example.vipa.model.Client;
import com.example.vipa.model.DialogType;
import com.example.vipa.service.DialogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {

    private final DialogService dialogService;

    @GetMapping("/{dialogId}")
    public String getDialogPage(Model model, @PathVariable("dialogId") int dialogId) {
        log.info("Получен запрос на просмотр диалога. dialogId: {}", dialogId);
        model.addAttribute("dialog", dialogService.getDialog(dialogId));
        model.addAttribute("message", new MessageDto());
        return "/dialog/dialog-page";
    }
/*    @ResponseBody
    @GetMapping(value = "/{dialogId}", produces = {"application/json; charset=UTF-8"})
    public DialogDetailsDto getDialogPage(@PathVariable("dialogId") int dialogId) {
        log.info("Получен запрос на просмотр диалога. dialogId: {}", dialogId);
        return dialogService.getDialog(dialogId);
    }*/

    @GetMapping("/list")
    public String getDialogsPage(Model model, @AuthenticationPrincipal Client currentClient,
                                 @RequestParam(value = "dialogType", defaultValue = "AS_CUSTOMER") DialogType dialogType) {
        log.info("Получен запрос на просмотр списка диалогов. currentClient: {}, dialogType: {}", currentClient, dialogType);
        if (dialogType.equals(DialogType.AS_CUSTOMER)) {
            model.addAttribute("dialogs", dialogService.getCustomerDialogs(currentClient.getId()));
        } else {
            model.addAttribute("dialogs", dialogService.getSellerDialogs(currentClient.getId()));
        }
        return "/dialog/dialogs-page";
    }
/*    @ResponseBody
    @GetMapping(value = "/list/{clientId}", produces = {"application/json; charset=UTF-8"})
    public List<DialogPreviewDto> getDialogsPage(@PathVariable("clientId") int clientId,
                                                 @RequestParam("dialogType") DialogType dialogType) {
        log.info("Получен запрос на просмотр списка диалогов. clientId: {}, dialogType: {}", clientId, dialogType);
        if (dialogType.equals(DialogType.AS_CUSTOMER)) {
            return dialogService.getCustomerDialogs(clientId);
        } else {
            return dialogService.getSellerDialogs(clientId);
        }
    }*/

    @PostMapping("/new/{postId}")
    public String createDialog(Model model, @AuthenticationPrincipal Client currentClient,
                               @PathVariable("postId") int postId) {
        log.info("Получен запрос на создание нового диалога. currentClient: {}, postId: {}", currentClient, postId);
        DialogDetailsDto dialogDetailsDto = dialogService.createDialog(currentClient.getId(), postId);
        model.addAttribute("dialog", dialogDetailsDto);
        return "redirect:/dialogs/" + dialogDetailsDto.getId();
    }
/*    @ResponseBody
    @PostMapping(value = "/new/{clientId}/{postId}", produces = {"application/json; charset=UTF-8"})
    public DialogDetailsDto createDialog(@PathVariable("clientId") int clientId,
                                          @PathVariable("postId") int postId) {
        log.info("Получен запрос на создание нового диалога. clientId: {}, postId: {}", clientId, postId);
        return dialogService.createDialog(clientId, postId);
    }*/
}
