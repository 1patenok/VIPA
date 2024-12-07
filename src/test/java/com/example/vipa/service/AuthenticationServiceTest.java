package com.example.vipa.service;

import com.example.vipa.mapper.ClientMapper;
import com.example.vipa.repository.ClientRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private ClientMapper clientMapperMock;

    @Mock
    private ClientRepository clientRepositoryMock;

    @InjectMocks
    private AuthenticationService authService;

    public void signIn_emailExists_returnsC


}
