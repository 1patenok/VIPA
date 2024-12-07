package com.example.vipa.service;

import com.example.vipa.dto.NewClientDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.mapper.ClientMapper;
import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Slf4j
@Service // Component
@RequiredArgsConstructor
public class ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final SimpleDateFormat OUTPUT_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    public Client getClient(int clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Пользователь с указанным id не найден."));
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createNewClient(NewClientDto newClientDto) {
//        log.info("newClient: {}", newClientDto);
//        if (!newClientDto.getPassword().equals(newClientDto.getPasswordConfirmation())) {
//            throw new RuntimeException("Пароли не совпадают.");
//        }
        clientRepository.findByEmail(newClientDto.getEmail())
                .ifPresent(client -> new RuntimeException("Пользователь с email " + client.getEmail() + " уже зарегистрирован."));
        String hashedPassword = passwordEncoder.encode(newClientDto.getPassword());
        newClientDto.setPassword(hashedPassword);




        return clientRepository.save(clientMapper.convertToClient(newClientDto));
    }

//    public Client updateClientInfo(int clientId, Client updatedClient) {
//        updatedClient.setId(clientId);
//        return clientRepository.save(updatedClient);
//    }

    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

    public Client signIn(SignInDto signInDto) {
        Client client = clientRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь с таким адресом не найден"));
        log.info("signInDto: {}", signInDto);
        String hashedPassword = passwordEncoder.encode(signInDto.getPassword());
        System.out.println(hashedPassword);

        if (passwordEncoder.matches(client.getPassword(), hashedPassword))
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
