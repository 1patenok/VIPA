package com.example.vipa.service;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.mapping.ClientMapper;
import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j // для логирования
@Service // = Component (чтобы автоматически создать объект класса AuthenticationService и поместить его в контекст приложения Spring)
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class AuthenticationService {

    private final ClientMapper clientMapper; // маппер для преобразования из ClientDetailsDto в Client
    private final BCryptPasswordEncoder passwordEncoder; // кодировщик паролей
    private final ClientRepository clientRepository; // репозиторий для взаимодействия с БД

    /**
     * Метод, выполняющий вход в аккаунт зарегистрированного пользователя.
     * @param signInDto - dto, в котором хранятся учетные данные пользователя
     * @return - возвращаем пользователя, если учетные данные указаны верно, в противном
     * случае выбрасываем исключение RuntimeException.
     */
    public ClientDetailsDto signIn(SignInDto signInDto) {
        log.info("signInDto: {}", signInDto); // выводим в логи данные, переданные в метод (просто чтобы посмотреть)
        Client client = clientRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь " + signInDto.getEmail() + " не найден."));
        if (passwordEncoder.matches(signInDto.getPassword(), client.getPassword())) {
            log.info("Проверка пароля пройдена.");
            return clientMapper.convertToClientDetailsDto(client);
        } else {
            log.info("Проверка пароля не пройдена.");
            throw new RuntimeException("Вы ввели неверный пароль.");
        }
    }

    /**
     * Метод, выполняющий регистрацию нового пользователя.
     * @param clientDetailsDto - dto, в котором хранятся все необходимые для регистрации пользователя данные
     * @return - возвращаем зарегистрированного пользователя.
     */
    public ClientDetailsDto signUp(ClientDetailsDto clientDetailsDto) {
        log.info("newClient: {}", clientDetailsDto); // выводим в логи данные, переданные в метод (просто чтобы посмотреть)
        if (!clientDetailsDto.getPassword().equals(clientDetailsDto.getPasswordConfirmation())) {
            throw new RuntimeException("Пароли не совпадают.");
        }
        if (clientRepository.existsByEmail(clientDetailsDto.getEmail())) {
            throw new RuntimeException("Пользователь с указанным email уже зарегистрирован.");
        }
        Client clientToSave = clientMapper.convertToClient(clientDetailsDto); // преобразуем NewClientDto в Client
        clientToSave.setPassword(passwordEncoder.encode(clientToSave.getPassword())); // кодируем пароль и записываем в сохраняемого клиента
        return clientMapper.convertToClientDetailsDto(clientRepository.save(clientToSave));
    }
}
