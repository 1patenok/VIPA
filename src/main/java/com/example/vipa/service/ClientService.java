package com.example.vipa.service;

import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;


    public Client getClient(int clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Пользователь с указанным id не найден."));
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createNewClient(Client newClient) {
        log.info("newClient: {}", newClient);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(newClient.getPassword());
        newClient.setPassword(hashedPassword);
        return clientRepository.save(newClient);
    }

//    public Client updateClientInfo(int clientId, Client updatedClient) {
//        updatedClient.setId(clientId);
//        return clientRepository.save(updatedClient);
//    }

    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    public Client signIn(String email, String password) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким адресом не найден"));
        System.out.println(client.getEmail());
        System.out.println(client.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(password);



        if (passwordEncoder.matches(password, hashedPassword))
            return client;
        else {
            throw new RuntimeException("Неверный пароль");
        }
    }

//    public Client signIn(String email, String password) {
//        Client client = clientRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Пользователь с таким адресом не найден"));
//        if (password.equals(client.getPassword()))
//            return client;
//        else {
//            throw new RuntimeException("Неверный пароль");
//        }
//    }
}
