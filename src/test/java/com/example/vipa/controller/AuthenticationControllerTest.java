package com.example.vipa.controller;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String SIGN_IN_PAGE_VIEW = "/auth/sign-in-page";
    private static final String SIGN_UP_PAGE_VIEW = "/auth/sign-up-page";
    private static final String HOMEPAGE_VIEW = "/common/homepage";

    @MockBean
    private AuthenticationService authServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getSignInPage_returnsStatusOkAndSignInPageView() throws Exception {
        mockMvc.perform(get("/auth/sign-in"))
                .andExpect(status().isOk())
                .andExpect(view().name(SIGN_IN_PAGE_VIEW))
                .andExpect(model().attributeExists("signInDto"));
    }

    @Test
    void getSignUpPage_returnsStatusOkAndSignUpPageView() throws Exception {
        mockMvc.perform(get("/auth/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name(SIGN_UP_PAGE_VIEW))
                .andExpect(model().attributeExists("clientDetailsDto"));
    }

    @Test
    void signIn_returnsStatusOkAndHomepageView() throws Exception {
        when(authServiceMock.signIn(any())).thenReturn(new ClientDetailsDto());

        mockMvc.perform(post("/auth/sign-in")
                        .formField("email", EMAIL)
                        .formField("password", PASSWORD))
                .andExpect(status().isOk())
                .andExpect(view().name(HOMEPAGE_VIEW))
                .andExpect(model().attributeExists("signInDto"));
        ArgumentCaptor<SignInDto> signInCaptor = ArgumentCaptor.forClass(SignInDto.class);
        verify(authServiceMock).signIn(signInCaptor.capture());
        SignInDto capturedSignInDto = signInCaptor.getValue();
        assertEquals(EMAIL, capturedSignInDto.getEmail());
        assertEquals(PASSWORD, capturedSignInDto.getPassword());
    }

    @Test
    void signUp_returnsStatusOkAndHomepageView() throws Exception {
        when(authServiceMock.signUp(any())).thenReturn(new ClientDetailsDto());

        mockMvc.perform(post("/auth/sign-up")
                        .formField("name", NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(HOMEPAGE_VIEW))
                .andExpect(model().attributeExists("clientDetailsDto"));
        ArgumentCaptor<ClientDetailsDto> clientDetailsCaptor = ArgumentCaptor.forClass(ClientDetailsDto.class);
        verify(authServiceMock).signUp(clientDetailsCaptor.capture());
        ClientDetailsDto capturedClientDetailsDto = clientDetailsCaptor.getValue();
        assertEquals(NAME, capturedClientDetailsDto.getName());
        assertNull(capturedClientDetailsDto.getEmail());
    }
}
