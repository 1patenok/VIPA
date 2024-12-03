package com.example.vipa.service;

import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким адресом не найден"));
    }

    public Client signIn(String email, String password) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким адресом не найден"));
        if (password.equals(client.getPassword()))
            return client;
        else {
            throw new RuntimeException("Неверный пароль");
        }
    }

}
