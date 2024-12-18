package com.example.vipa.validators;

import com.example.vipa.dto.SignInDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationValidator {
    private final ClientRepository clientRepository;

    /**
     * Валидация запроса на вход в аккаунт.
     */
    public void performSignInValidation(SignInDto request) {
        String email = request.getUsername();
        if (clientRepository.findByEmail(email).isEmpty()) {
            throw new NotFoundException("Пользователь с указанным email не найден.");
        }
    }
}
