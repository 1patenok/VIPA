package com.example.vipa.validation;

import com.example.vipa.dto.client.ClientDetailsDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.service.ClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ClientValidator implements Validator {

    private static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Пароль и его подтверждение не совпадают.";
    private static final String CLIENT_ALREADY_EXIST_MESSAGE = "Клиент с указанным email уже существует.";

    private final ClientDetailsService clientDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDetailsDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientDetailsDto client = (ClientDetailsDto) target;
        if (!client.getPassword().equals(client.getPasswordConfirmation())) {
            errors.rejectValue("password", HttpStatus.BAD_REQUEST.name(), PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }
        try {
            clientDetailsService.loadUserByUsername(client.getEmail());
        } catch (NotFoundException e) {
            errors.rejectValue("email", HttpStatus.ALREADY_REPORTED.name(), CLIENT_ALREADY_EXIST_MESSAGE);
        }
    }

}
