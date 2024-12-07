package com.example.vipa.controller;

import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.service.FavoritesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FavoritesController.class)
public class FavoritesControllerTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer POST_ID = 1;
    private static final String FAVORITES_PAGE_VIEW = "/post/favorites-page";
    private static final String ADD_TO_FAVORITES_SUCCESS_MESSAGE = "Объявление успешно добавлено в избранное.";
    private static final String DELETE_FROM_FAVORITES_SUCCESS_MESSAGE = "Объявление успешно удалено из избранного.";

    @MockBean
    private FavoritesService favoritesServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getFavoritesPage_serviceWorkedCorrectly_returnsStatusOkAndFavoritesPageView() {
        List<PostPreviewDto> dtos = List.of(new PostPreviewDto());
        when(favoritesServiceMock.getFavorites(CLIENT_ID)).thenReturn(dtos);

        mockMvc.perform(get("/favorites/{clientId}", CLIENT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(FAVORITES_PAGE_VIEW))
                .andExpect(model().attribute("favorites", dtos));
        verify(favoritesServiceMock, times(1)).getFavorites(CLIENT_ID);
    }

    @Test
    @SneakyThrows
    void getFavoritesPage_serviceThrewAnException_returnsStatus500AndErrorMessage() {
        // todo
    }

    @Test
    @SneakyThrows
    void addPostToFavorites_serviceWorkedCorrectly_returnsStatusOkAndSuccessMessage() {
        mockMvc.perform(post("/favorites/{clientId}/{postId}", CLIENT_ID, POST_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(ADD_TO_FAVORITES_SUCCESS_MESSAGE));
        verify(favoritesServiceMock, times(1)).addPostToFavorites(CLIENT_ID, POST_ID);
    }

    @Test
    @SneakyThrows
    void addPostToFavorites_serviceThrewAnException_returnsErrorStatusAndMessage() {
        // todo
    }

    @Test
    @SneakyThrows
    void deletePostFromFavorites_serviceWorkedCorrectly_returnsStatusOkAndSuccessMessage() {
        mockMvc.perform(delete("/favorites/{clientId}/{postId}", CLIENT_ID, POST_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(DELETE_FROM_FAVORITES_SUCCESS_MESSAGE));
        verify(favoritesServiceMock, times(1)).deletePostFromFavorites(CLIENT_ID, POST_ID);
    }

    @Test
    @SneakyThrows
    void deletePostFromFavorites_serviceWorkedCorrectly_returnsErrorStatusAndMessage() {
        // todo
    }
}
