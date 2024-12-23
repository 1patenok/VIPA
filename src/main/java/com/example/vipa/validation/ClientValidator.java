package com.example.vipa.validation;

import com.example.vipa.dto.client.ClientDetailsDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.model.Client;
import com.example.vipa.service.ClientDetailsService;
import com.example.vipa.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ClientValidator implements Validator {

    private static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Пароль и его подтверждение не совпадают.";
    private static final String EMAIL_ALREADY_EXIST_MESSAGE = "Клиент с указанным email уже существует.";
    private static final String PHONE_NUMBERR_ALREADY_EXIST_MESSAGE = "Клиент с указанным номером телефона уже существует.";

    private final ClientDetailsService clientDetailsService;

    private final ClientService clientService;

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
        if (clientService.getClientEntityByPhoneNumber(client.getPhoneNumber()) != null) {
            errors.rejectValue("phoneNumber", HttpStatus.ALREADY_REPORTED.name(), PHONE_NUMBERR_ALREADY_EXIST_MESSAGE);
        }
        try {
            UserDetails userDetails = clientDetailsService.loadUserByUsername(client.getEmail());
            if (userDetails != null) {
                errors.rejectValue("email", HttpStatus.ALREADY_REPORTED.name(), EMAIL_ALREADY_EXIST_MESSAGE);
            }
        } catch (Exception e) {
        }

    }

}
