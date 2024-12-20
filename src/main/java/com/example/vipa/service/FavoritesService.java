package com.example.vipa.service;

import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final PostMapper postMapper;
    private final ClientService clientService;
    private final PostService postService;

    @Transactional(readOnly = true)
    public List<PostPreviewDto> getFavorites(int clientId) {
        return clientService.getClientEntity(clientId)
                .getFavoritePosts().stream()
                .map(postMapper::convertToPostPreviewDto)
                .toList();
    }

    @Transactional
    public boolean isPostInFavorites(int clientId, int postId) {
        Post result = clientService.getClientEntity(clientId).getFavoritePosts().stream()
                .filter(post -> post.getId() == postId)
                .findAny().orElse(new Post().setId(0));
        return result.getId() != 0;
    }

    @Transactional
    public void addPostToFavorites(int clientId, int postId) {
        Client client = clientService.getClientEntity(clientId);
        Post post = postService.getPostEntity(postId);
        client.addFavorite(post);
        //post.addClientWithPostInFavorites(client);
        clientService.updateClient(client);
        //postService.updatePost(post);
    }

    @Transactional
    public void deletePostFromFavorites(int clientId, int postId) {
        Client client = clientService.getClientEntity(clientId);
        Post post = postService.getPostEntity(postId);
        client.removeFavorite(post);
        //post.removeClientWithPostInFavorites(client);
        clientService.updateClient(client);
        //postService.updatePost(post);
    }
}
