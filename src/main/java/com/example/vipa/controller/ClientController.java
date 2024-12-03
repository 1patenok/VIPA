package com.example.vipa.controller;

import com.example.vipa.dto.SignInDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/getStyles")
    public String getStyles() {
        return "registration/style.css";
    }

    @GetMapping("/{clientId}")
    public String getClient(Model model, @PathVariable(name = "clientId") int clientId) {
        Client client = clientService.getClient(clientId);
        model.addAttribute("client", client);
        return "client/index";
    }

    @GetMapping
    public String getClients(Model model) {
        Iterable<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "client/show";
    }

    @GetMapping("/new")
    public String newClient(Model model) {
        System.out.println("Inside newClient()");
        model.addAttribute("client", new Client());
        return "registration/create_account";
    }

//    @GetMapping("/edit/{clientId}")
//    public String editClient(Model model, @PathVariable("clientId") int clientId) {
//        Client client = clientService.getClient(clientId);
//        model.addAttribute("client", client);
//        return "client/edit";
//    }

    @GetMapping("/logIn")
    public String showSignIn(Model model) {
        model.addAttribute("signIn", new SignInDto());
        System.out.println("Inside showSignIn()");
        return "registration/logIn";
    }

    @PostMapping("/logIn")
    public String signIn(Model model, SignInDto signInDto) {
        System.out.println(signInDto.getEmail() + "");
        System.out.println(signInDto.getPassword() + "");
        Client result = clientService.signIn(signInDto.getEmail(), signInDto.getPassword());
        model.addAttribute("client", result);
        return "redirect:/clients/registration/homepage";

    }

    @GetMapping("/registration/homepage")
    public String showHomePage() {
        System.out.println("inside showHomePage()");
        return "registration/homepage";
    }

    @PostMapping("/signUp")
    public String addClient(@ModelAttribute("client") Client client) {
        System.out.println("Получили из формы: " + client);
        clientService.createNewClient(client);
        System.out.println("Пользователь успешно зарегистрирован.");
        return "redirect:/clients/registration/homepage";
    }

//    @PatchMapping("/{clientId}")
//    public Client updateClient(@PathVariable("clientId") int clientId,
//                               Client client) {
//        return clientService.updateClientInfo(clientId, client);
//    }
//
//    @DeleteMapping("/delete/{clientId}")
//    public void deleteClient(@PathVariable("clientId") int clientId){
//        clientService.deleteClient(clientId);
//    }

}
