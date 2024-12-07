package com.example.vipa.mapper;

import com.example.vipa.dto.PostDetailsDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public Post convertToPost(PostDetailsDto dto) {
        return modelMapper.map(dto, Post.class);
    }

    public PostPreviewDto convertToPostPreviewDto(Post post) {
        return modelMapper.map(post, PostPreviewDto.class);
    }

    public PostDetailsDto convertToPostDetailsDto(Post post) {
        return modelMapper.map(post, PostDetailsDto.class);
    }
}
