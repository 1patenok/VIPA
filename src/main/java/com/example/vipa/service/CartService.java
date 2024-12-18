package com.example.vipa.service;

import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import com.example.vipa.repository.ClientRepository;
import com.example.vipa.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final PostMapper postMapper;
    private final ClientService clientService;
    private final PostService postService;
    private final OrderValidator orderValidator;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<PostPreviewDto> getProductsInCart(int clientId) {
        return clientService.getClientEntity(clientId)
                .getPostsInCart().stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public void addPostToCart(int clientId, int postId) {
        // Вызов валидатора перед добавлением
        orderValidator.validateAddToCart(clientId, postId);

        Client client = clientService.getClientEntity(clientId);
        Post post = postService.getPostEntity(postId);

        client.addToCart(post);
        clientService.updateClient(client);
    }

    @Transactional
    public void deletePostFromCart(int clientId, int postId) {
        Client client = clientService.getClientEntity(clientId);
        Post post = postService.getPostEntity(postId);

        // Проверка на наличие товара в корзине
        if (!client.getPostsInCart().contains(post)) {
            throw new IllegalArgumentException("Товар отсутствует в корзине.");
        }

        client.getPostsInCart().remove(post);
        clientService.updateClient(client);
    }
}
