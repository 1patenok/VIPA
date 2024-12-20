package com.example.vipa.controller;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.ClientService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j // для логирования
@Controller // = Component (чтобы автоматически создать объект класса ClientController и поместить его в контекст приложения Spring)
@RequestMapping("/clients") // все запросы, начинающиеся с http://localhost:8080/clients обрабатываются данным контроллером
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class ClientController {

    private final PostService postService;
    private final ClientService clientService;

    /**
     * Данный метод вызывается, когда нужно получить веб-страницу с данными о пользователе.
     * @param model - изначально модель пустая, в методе в нее добавляется объект ClientDetailsDto,
     *              данные которого будут отображены на веб-странице
     * @param clientId - id пользователя, данные о котором нужно получить
     * @return - возвращаем название представления (html-файла), в котором отображаются данные пользователя
     */
    @GetMapping("/{clientId}")
    public String getClientPage(Model model, @PathVariable("clientId") int clientId) {
        log.info("Получен запрос на просмотр профиля клиента. clientId: {}", clientId);
        ClientDetailsDto client = clientService.getClient(clientId);
        model.addAttribute("client", client);
        model.addAttribute("posts", postService.getPostsByAuthor(clientId));
        return "/client/client-page";
    }

    @GetMapping("/edit")
    public String getEditClientPage(Model model, @AuthenticationPrincipal Client currentClient) {
        log.info("Получен запрос на получение формы для изменения данных клиента. currentClient: {}", currentClient);
        model.addAttribute("client", clientService.getClient(currentClient.getId()));
        return "/client/edit-client-page";
    }

    @PutMapping("/edit")
    public String updateClient(@AuthenticationPrincipal Client currentClient,
                               @ModelAttribute("client") ClientDetailsDto clientDetailsDto) {
        log.info("Получен запрос на обновление данных клиента. currentClient: {}, clientDetailsDto: {}", currentClient, clientDetailsDto);
        clientService.updateClient(currentClient.getId(), clientDetailsDto);
        return "/client/client-page";
    }

    /**
     * Данный метод вызывается, когда нужно удалить пользователя.
     * @param clientId - id пользователя, которого нужно удалить
     * @return - возвращаем название представления для homepage
     */
    @DeleteMapping("/delete/{clientId}")
    public String deleteClient(@PathVariable("clientId") int clientId,
                               @AuthenticationPrincipal Client currentClient){
        log.info("Получен запрос на удаление пользователя. clientId: {}", clientId);
        clientService.deleteClient(clientId);
        if (currentClient.getId() == clientId) {
            return "redirect:/homepage-guest";
        } else {
            return "redirect:/homepage-client";
        }
    }

}
