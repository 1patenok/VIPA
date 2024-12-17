package com.example.vipa.service;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.mapping.ClientMapper;
import com.example.vipa.model.Client;
import com.example.vipa.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "testPassword";
    private static final String OTHER_PASSWORD = "otherPassword";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private ClientMapper clientMapperMock;

    @Mock
    private BCryptPasswordEncoder passwordEncoderMock;

    @Mock
    private ClientRepository clientRepositoryMock;

    @InjectMocks
    private AuthenticationService authService;

    @Test
    void signIn_emailExistsAndPasswordIsCorrect_returnsClientDetailsDto() {
        SignInDto signInDto = new SignInDto()
                .setUsername(EMAIL).setPassword(PASSWORD);
        Client client = new Client()
                .setPassword(PASSWORD);
        ClientDetailsDto clientDetailsDto = new ClientDetailsDto();
        when(clientRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(client));
        when(passwordEncoderMock.matches(signInDto.getPassword(), client.getPassword())).thenReturn(true);
        when(clientMapperMock.convertToClientDetailsDto(client)).thenReturn(clientDetailsDto);

        ClientDetailsDto result = authService.signIn(signInDto);

        assertEquals(clientDetailsDto, result);
        verify(clientRepositoryMock, times(1)).findByEmail(EMAIL);
        verify(passwordEncoderMock, times(1)).matches(PASSWORD, PASSWORD);
        verify(clientMapperMock, times(1)).convertToClientDetailsDto(client);
    }

    @Test
    public void signIn_emailDoesNotExist_throwsRuntimeException() {
        SignInDto signInDto = new SignInDto()
                .setUsername(EMAIL).setPassword(PASSWORD);
        when(clientRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.signIn(signInDto),
                "Пользователь " + EMAIL + " не найден.");
        verify(clientRepositoryMock).findByEmail(EMAIL);
        verify(passwordEncoderMock, never()).matches(any(), any());
        verify(clientMapperMock, never()).convertToClientDetailsDto(any());

    }

    @Test
    public void signIn_emailExistsAndPasswordIsIncorrect_throwsRuntimeException() {
        SignInDto signInDto = new SignInDto()
                .setUsername(EMAIL).setPassword(PASSWORD);
        Client client = new Client()
                .setPassword(OTHER_PASSWORD);
        when(clientRepositoryMock.findByEmail(EMAIL)).thenReturn(Optional.of(client));
        when(passwordEncoderMock.matches(signInDto.getPassword(), client.getPassword())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.signIn(signInDto),
                "Вы ввели неверный пароль.");
        verify(clientRepositoryMock).findByEmail(EMAIL);
        verify(passwordEncoderMock).matches(PASSWORD, OTHER_PASSWORD);
        verify(clientMapperMock, never()).convertToClientDetailsDto(any());
    }

    @Test
    public void signUp_passwordsDoNotMatch_throwsRuntimeException() {
        ClientDetailsDto clientDetailsDto = new ClientDetailsDto()
                .setPassword(PASSWORD).setPasswordConfirmation(OTHER_PASSWORD);

        assertThrows(RuntimeException.class, () -> authService.signUp(clientDetailsDto),
                "Пароли не совпадают.");
        verify(clientRepositoryMock, never()).existsByEmail(any());
        verify(clientMapperMock, never()).convertToClient(any());
        verify(passwordEncoderMock, never()).encode(any());
        verify(clientRepositoryMock, never()).save(any());
        verify(clientMapperMock, never()).convertToClientDetailsDto(any());
    }

    @Test
    public void signUp_passwordsMatchAndEmailDoesNotExist_returnsClientDetailsDto() {
        Client client = new Client()
                .setEmail(EMAIL).setPassword(PASSWORD);
        ClientDetailsDto clientDetailsDto = new ClientDetailsDto()
                .setEmail(EMAIL).setPassword(PASSWORD).setPasswordConfirmation(PASSWORD);
        when(clientRepositoryMock.existsByEmail(EMAIL)).thenReturn(false);
        when(clientMapperMock.convertToClient(clientDetailsDto)).thenReturn(client);
        when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(clientRepositoryMock.save(client)).thenReturn(client);
        ClientDetailsDto clientDetailsDto2 = new ClientDetailsDto()
                .setPassword(ENCODED_PASSWORD);
        when(clientMapperMock.convertToClientDetailsDto(client)).thenReturn(clientDetailsDto2);

        String result = authService.signUp(clientDetailsDto);

        assertEquals(clientDetailsDto2, result);
        //assertEquals(ENCODED_PASSWORD, result.getPassword());
        verify(clientRepositoryMock, times(1)).existsByEmail(EMAIL);
        verify(clientMapperMock, times(1)).convertToClient(clientDetailsDto);
        verify(passwordEncoderMock, times(1)).encode(PASSWORD);
        verify(clientRepositoryMock, times(1)).save(client);
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientMapperMock, times(1)).convertToClientDetailsDto(clientCaptor.capture());
        Client capturedClient = clientCaptor.getValue();
        assertEquals(capturedClient.getPassword(), ENCODED_PASSWORD);
    }

    @Test
    public void signUp_passwordsMatchAndEmailExists_throwsRuntimeException() {
        ClientDetailsDto clientDetailsDto = new ClientDetailsDto()
                .setEmail(EMAIL).setPassword(PASSWORD).setPasswordConfirmation(PASSWORD);
        when(clientRepositoryMock.existsByEmail(clientDetailsDto.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.signUp(clientDetailsDto),
                "Пользователь с указанным email уже зарегистрирован.");
        verify(clientRepositoryMock).existsByEmail(EMAIL);
        verify(clientMapperMock, never()).convertToClient(any());
        verify(clientRepositoryMock, never()).save(any());
        verify(clientMapperMock, never()).convertToClientDetailsDto(any());
    }

}
