package com.example.vipa.service;

import com.example.vipa.exception.NotFoundException;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Пользователь с указанным email не найден.";

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("email: {}", email);
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_MESSAGE));
    }
}
