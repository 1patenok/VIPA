
package com.example.vipa.validators;

import com.example.vipa.exception.NotFoundException;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import com.example.vipa.service.ClientService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {
    private final ClientService clientService;
    private final PostService postService;

    /**
     * Валидация добавления товара в корзину.
     *
     * @param clientId - ID клиента
     * @param postId   - ID товара
     */
    public void validateAddToCart(int clientId, int postId) {
        // Проверяем, существует ли клиент
        Client client = clientService.getClientEntity(clientId);
        if (client == null) {
            throw new NotFoundException("Клиент с ID " + clientId + " не найден.");
        }

        // Проверяем, существует ли товар
        Post post = postService.getPostEntity(postId);
        if (post == null) {
            throw new NotFoundException("Товар с ID " + postId + " не найден.");
        }

        // Проверка на дублирование в корзине
        if (client.getPostsInCart().contains(post)) {
            throw new IllegalArgumentException("Товар уже добавлен в корзину.");
        }

        // Проверка на корректность цены товара
        if (post.getPrice() < 0) {
            throw new IllegalArgumentException("Цена товара не может быть отрицательной.");
        }
    }
}
