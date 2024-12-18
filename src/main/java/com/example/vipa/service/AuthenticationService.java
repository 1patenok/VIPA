package com.example.vipa.service;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.exception.AlreadyExistException;
import com.example.vipa.exception.BadCredentialsException;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.exception.PasswordMismatchException;
import com.example.vipa.mapping.ClientMapper;
import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import com.example.vipa.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Пользователь не найден.";
    private static final String EMAIL_ALREADY_EXIST_MESSAGE = "Пользователь с указанным email уже зарегистрирован.";
    private static final String INVALID_PASSWORD_MESSAGE = "Неверный пароль.";
    private static final String WRONG_PASSWORD_CONFIRMATION_MESSAGE = "Пароли не совпадают.";

    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final PasswordValidator passwordValidator; // Сервис для проверки пароля

    /**
     * Метод, выполняющий вход в аккаунт зарегистрированного пользователя.
     * @param signInDto - dto, в котором хранятся учетные данные пользователя
     * @return - возвращаем пользователя, если учетные данные указаны верно, в противном случае выбрасываем исключение RuntimeException.
     */
    public ClientDetailsDto signIn(SignInDto signInDto) {
        log.info("Вход пользователя с email: {}", signInDto.getUsername());

        Client client = clientRepository.findByEmail(signInDto.getUsername())
                .orElseThrow(() -> new NotFoundException(CLIENT_NOT_FOUND_MESSAGE));

        if (passwordEncoder.matches(signInDto.getPassword(), client.getPassword())) {
            log.info("Проверка пароля пройдена успешно.");
            return clientMapper.convertToClientDetailsDto(client);
        } else {
            log.info("Неверный пароль для пользователя с email: {}", signInDto.getUsername());
            throw new BadCredentialsException(INVALID_PASSWORD_MESSAGE);
        }
    }

    /**
     * Метод, выполняющий регистрацию нового пользователя.
     * @param clientDetailsDto - dto, в котором хранятся все необходимые для регистрации пользователя данные
     * @return - возвращаем зарегистрированного пользователя.
     */
    public ClientDetailsDto signUp(ClientDetailsDto clientDetailsDto) {
        log.info("Регистрация нового пользователя с email: {}", clientDetailsDto.getEmail());

        // Проверка совпадения паролей
        if (!clientDetailsDto.getPassword().equals(clientDetailsDto.getPasswordConfirmation())) {
            log.error("Пароли не совпадают для email: {}", clientDetailsDto.getEmail());
            throw new PasswordMismatchException(WRONG_PASSWORD_CONFIRMATION_MESSAGE);
        }

        // Проверка существования пользователя с таким email
        if (clientRepository.existsByEmail(clientDetailsDto.getEmail())) {
            log.error("Пользователь с таким email уже существует: {}", clientDetailsDto.getEmail());
            throw new AlreadyExistException(EMAIL_ALREADY_EXIST_MESSAGE);
        }

        // Валидация пароля
        //passwordValidator.validatePassword(clientDetailsDto.getPassword());

        // Преобразуем DTO в модель клиента и кодируем пароль
        Client clientToSave = clientMapper.convertToClient(clientDetailsDto);
        clientToSave.setPassword(passwordEncoder.encode(clientDetailsDto.getPassword()));

        // Сохраняем клиента в базе данных
        Client savedClient = clientRepository.save(clientToSave);

        log.info("Новый пользователь успешно зарегистрирован с email: {}", savedClient.getEmail());
        return clientMapper.convertToClientDetailsDto(savedClient);
    }
}
