package com.example.vipa.service;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.mapping.ClientMapper;
import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j // для логирования
@Service // = Component (чтобы автоматически создать объект класса ClientService и поместить его в контекст приложения Spring)
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class ClientService {

    private final ClientMapper clientMapper; // для преобразования из ClientDetailsDto в Client и наоборот
    private final ClientRepository clientRepository;

    /**
     * Метод для получения пользователя по его id.
     * @param clientId - id пользователя
     * @return - возвращаем объект класса ClientDetailsDto
     */
    public ClientDetailsDto getClient(int clientId) {
        log.info("inside getClient(), clientId: {}", clientId);
        return clientRepository.findById(clientId).map(clientMapper::convertToClientDetailsDto)
                .orElseThrow(() -> new RuntimeException("Пользователь с указанным id не найден."));
    }

    /**
     * Метод для получения пользователя по его id.
     * @param clientId - id пользователя
     * @return - возвращаем объект класса Client
     */
    public Client getClientEntity(int clientId) {
        log.info("inside getClient(), clientId: {}", clientId);
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Пользователь с указанным id не найден."));
    }

    /**
     * Метод для сохранения клиента.
     * @param updatedClient - клиент, которого нужно обновить
     */
    public void updateClient(Client updatedClient) {
        clientRepository.save(updatedClient);
    }

    /**
     * Метод для обновления данных пользователя.
     * @param clientId - id пользователя, данные которого нужно обновить
     * @param clientDetailsDto - обновленные данные пользователя
     * @return - возвращаем объекта класса ClientDetailsDto (данные обновленного пользователя)
     */
    public ClientDetailsDto updateClient(int clientId, ClientDetailsDto clientDetailsDto) {
        log.info("inside updateClient(), clientId: {}, clientDetailsDto: {}", clientId, clientDetailsDto);
        if (!clientRepository.existsById(clientId)) {
            throw new RuntimeException("Пользователь с id=" + clientId + " не найден.");
        }
        Client updatedClient = clientMapper.convertToClient(clientDetailsDto);
        /* Чтобы понять, какого именно клиента нужно обновить, нужно присвоить clientId.
           Если не сделать это, то вместо обновления существующего клиента будет создан новый клиент.*/
        updatedClient.setClientId(clientId);
        return clientMapper.convertToClientDetailsDto(clientRepository.save(updatedClient));
    }

    /**
     * Метод для удаления пользователя по id.
     * @param clientId - id пользователя, которого нужно удалить.
     */
    public void deleteClient(int clientId) {
        log.info("inside deleteClient(), clientId: {}", clientId);
        clientRepository.deleteById(clientId);
    }
}
