package com.example.vipa.controller;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j // для логирования
@Controller // = Component (чтобы автоматически создать объект класса ClientController и поместить его в контекст приложения Spring)
@RequestMapping("/clients") // все запросы, начинающиеся с http://localhost:8080/clients обрабатываются данным контроллером
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class ClientController {

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
        log.info("inside getClientPage(), clientId: {}", clientId);
        ClientDetailsDto client = clientService.getClient(clientId);
        model.addAttribute("client", client);
        return "/client/client-page";
    }

    /**
     * Данный метод вызывается, когда нужно получить веб-страницу для редактирования данных о пользователе.
     * @param model - изначально модель пустая, в методе в нее добавляется объект ClientDetailsDto,
     *              данные которого нужно изменить
     * @param clientId - id пользователя, данные которого нужно изменить
     * @return - возвращаем название представления (html-файла), в котором происходит редактирование пользователя
     */
    @GetMapping("/edit/{clientId}")
    public String getEditClientPage(Model model, @PathVariable("clientId") int clientId) {
        log.info("inside getEditClientPage(), clientId: {}", clientId);
        ClientDetailsDto client = clientService.getClient(clientId);
        model.addAttribute("client", client);
        return "/client/edit-client-page";
    }

    /**
     * Данный метод вызывается, когда на сервер отправляется заполненная форма для обновления данных пользователя.
     * @param model - изначально модель пустая, в методе в нее помещается объект с обновленными данными пользователя.
     * @param clientId - id пользователя, данные которого нужно изменить
     * @param clientDetailsDto - измененные данные пользователя, полученные из формы
     * @return - возвращаем название представления, в котором отображаются обновленные данные пользователя
     */
    @PutMapping("/edit/{clientId}")
    public String updateClient(Model model, @PathVariable("clientId") int clientId,
                               @ModelAttribute("client") ClientDetailsDto clientDetailsDto) {
        log.info("inside updateClient(), clientId: {}, clientDetailsDto: {}", clientId, clientDetailsDto);
        ClientDetailsDto updatedClient = clientService.updateClient(clientId, clientDetailsDto);
        model.addAttribute("client", updatedClient);
        return "/client/client-page";
    }

    /**
     * Данный метод вызывается, когда нужно удалить пользователя.
     * @param clientId - id пользователя, которого нужно удалить
     * @return - возвращаем название представления для homepage
     */
    @DeleteMapping("/delete/{clientId}")
    public String deleteClient(@PathVariable("clientId") int clientId){
        log.info("inside deleteClient(), clientId: {}", clientId);
        clientService.deleteClient(clientId);
        return "/common/homepage";
    }

}
