package com.example.vipa.mapping;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostImageDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import com.example.vipa.model.PostImage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    private static final Integer POST_IMAGE_ID = 1;
    private static final Integer POST_IMAGE_OTHER_ID = 2;
    private static final String POST_IMAGE_URL = "url1";
    private static final String POST_IMAGE_OTHER_URL = "url2";
    private static final Integer POST_ID = 1;
    private static final Integer POST_PRICE = 1000;
    private static final String POST_TITLE = "title";
    private static final String POST_DESCRIPTION = "description";
    private static final String POST_STATUS = "status";
    private static final String POST_ADDRESS = "address";

    private static ModelMapper modelMapper;

    @BeforeAll
    public static void init() {
        modelMapper = new ModelMapper();
        Converter<List<PostImage>, PostImageDto> getFirstImageConverter = src ->
                modelMapper.map(src.getSource().get(0), PostImageDto.class);
        Converter<List<PostImage>, List<PostImageDto>> imageListToImageDtoListConverter =
                src -> src.getSource().stream()
                        .map(image -> modelMapper.map(image, PostImageDto.class))
                        .toList();
        modelMapper.createTypeMap(Post.class, PostPreviewDto.class)
                .addMappings(mapper ->
                        mapper.using(getFirstImageConverter).map(Post::getImages, PostPreviewDto::setCoverImage));
        modelMapper.createTypeMap(Post.class, PostDetailsDto.class)
                .addMappings(mapper -> mapper.using(imageListToImageDtoListConverter)
                        .map(Post::getImages, PostDetailsDto::setImages));
    }

    @Test
    void convertToPost_returnsCorrectPost() {
        List<PostImageDto> postImageDtos = List.of(
                new PostImageDto().setPostImageId(POST_IMAGE_ID).setUrl(POST_IMAGE_URL),
                new PostImageDto().setPostImageId(POST_IMAGE_OTHER_ID).setUrl(POST_IMAGE_OTHER_URL));
        PostDetailsDto postDetailsDto = new PostDetailsDto()
                .setPostId(POST_ID).setTitle(POST_TITLE).setPrice(POST_PRICE)
                .setStatus(POST_STATUS).setDescription(POST_DESCRIPTION)
                .setAddress(POST_ADDRESS).setImages(postImageDtos);

        Post resultPost = modelMapper.map(postDetailsDto, Post.class);

        assertEquals(POST_ID, resultPost.getPostId());
        assertEquals(POST_TITLE, resultPost.getTitle());
        assertEquals(POST_PRICE, resultPost.getPrice());
        assertEquals(POST_STATUS, resultPost.getStatus());
        assertEquals(POST_DESCRIPTION, resultPost.getDescription());
        assertEquals(POST_ADDRESS, resultPost.getAddress());
        assertEquals(POST_IMAGE_ID, resultPost.getImages().get(0).getPostImageId());
        assertEquals(POST_IMAGE_URL, resultPost.getImages().get(0).getUrl());
        assertEquals(POST_IMAGE_OTHER_ID, resultPost.getImages().get(1).getPostImageId());
        assertEquals(POST_IMAGE_OTHER_URL, resultPost.getImages().get(1).getUrl());
    }

    @Test
    void convertToPostPreviewDto_returnsCorrectPostPreviewDto() {
        List<PostImage> postImages = List.of(
                new PostImage().setPostImageId(POST_IMAGE_ID).setUrl(POST_IMAGE_URL),
                new PostImage());
        Post post = new Post()
                .setPostId(POST_ID).setTitle(POST_TITLE).setPrice(POST_PRICE)
                .setStatus(POST_STATUS).setDescription(POST_DESCRIPTION)
                .setAddress(POST_ADDRESS).setAuthor(new Client()).setImages(postImages);

        PostPreviewDto resultDto = modelMapper.map(post, PostPreviewDto.class);

        assertEquals(POST_ID, resultDto.getPostId());
        assertEquals(POST_TITLE, resultDto.getTitle());
        assertEquals(POST_PRICE, resultDto.getPrice());
        assertEquals(POST_ADDRESS, resultDto.getAddress());
        assertEquals(POST_IMAGE_ID, resultDto.getCoverImage().getPostImageId());
        assertEquals(POST_IMAGE_URL, resultDto.getCoverImage().getUrl());
    }

    @Test
    void convertToPostDetailsDto_returnsCorrectPostDetailsDto() {
        List<PostImage> postImages = List.of(
                new PostImage().setPostImageId(POST_IMAGE_ID).setUrl(POST_IMAGE_URL),
                new PostImage().setPostImageId(POST_IMAGE_OTHER_ID).setUrl(POST_IMAGE_OTHER_URL));
        Post post = new Post()
                .setPostId(POST_ID).setTitle(POST_TITLE).setPrice(POST_PRICE)
                .setStatus(POST_STATUS).setDescription(POST_DESCRIPTION)
                .setAddress(POST_ADDRESS).setAuthor(new Client()).setImages(postImages);

        PostDetailsDto resultDto = modelMapper.map(post, PostDetailsDto.class);

        assertEquals(POST_ID, resultDto.getPostId());
        assertEquals(POST_TITLE, resultDto.getTitle());
        assertEquals(POST_PRICE, resultDto.getPrice());
        assertEquals(POST_STATUS, resultDto.getStatus());
        assertEquals(POST_DESCRIPTION, resultDto.getDescription());
        assertEquals(POST_ADDRESS, resultDto.getAddress());
        assertEquals(POST_IMAGE_ID, resultDto.getImages().get(0).getPostImageId());
        assertEquals(POST_IMAGE_URL, resultDto.getImages().get(0).getUrl());
        assertEquals(POST_IMAGE_OTHER_ID, resultDto.getImages().get(1).getPostImageId());
        assertEquals(POST_IMAGE_OTHER_URL, resultDto.getImages().get(1).getUrl());
    }
}
