package com.example.vipa.controller;

import com.example.vipa.model.Client;
import com.example.vipa.service.AuthenticationService;
import com.example.vipa.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ClientService clientService;

//    @PostMapping("/signUp")
//    public Client signUp(@RequestBody Client client){
//        Client resultClient = clientService.createNewClient(client);
//        return resultClient;
//    }

    @GetMapping("/signUp")
    public String signUp(Model model /*@RequestBody Client client*/){
        //model.addAttribute("client", clientService.createNewClient(client));
        return "registration/create_account";
    }

    // @GetMapping("/signIn")
    //@PostMapping("/login")
    public Client signIn(@RequestBody String email, @RequestBody String password){
        Client result = authService.signIn(email, password);

        return result;
    }
}
