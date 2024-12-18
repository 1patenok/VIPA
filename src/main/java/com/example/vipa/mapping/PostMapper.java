package com.example.vipa.mapping;

import com.example.vipa.dto.PostDetailsInputDto;
import com.example.vipa.dto.PostDetailsOutputDto;
import com.example.vipa.dto.PostPreviewDto;
import com.example.vipa.model.Client;
import com.example.vipa.model.Post;
import com.example.vipa.model.PostImage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        Converter<List<PostImage>, String> imageListToFirstImageURIConverter =
                src -> src.getSource().isEmpty() ? null : src.getSource().get(0).getUrl();
        Converter<Client, String> clientToClientNameConverter =
                src -> modelMapper.map(src.getSource().getName(), String.class);
        Converter<List<PostImage>, List<String>> imageListToPathList =
                src -> src.getSource().stream()
                        .map(PostImage::getUrl)
                        .toList();
/*        Converter<List<MultipartFile>, List<PostImage>> multipartFileListToPostImageListConverter =
                src -> src.getSource().stream()
                        .map(multipart -> new PostImage().setUrl(multipart.getOriginalFilename()))
                        .toList();*/
        modelMapper.createTypeMap(Post.class, PostPreviewDto.class)
                .addMappings(mapper -> {
                    mapper.using(imageListToFirstImageURIConverter)
                            .map(Post::getImages, PostPreviewDto::setCoverImagePath);
                    mapper.using(clientToClientNameConverter)
                            .map(Post::getAuthor, PostPreviewDto::setAuthorName);
                });
        modelMapper.createTypeMap(Post.class, PostDetailsOutputDto.class)
                .addMappings(mapper -> mapper.using(imageListToPathList)
                        .map(Post::getImages, PostDetailsOutputDto::setImagePaths));
        modelMapper.createTypeMap(PostDetailsInputDto.class, Post.class)
                .addMappings(mapper -> mapper.skip(PostDetailsInputDto::getImages, Post::setImages));
    }

    public Post convertToPost(PostDetailsInputDto dto) {
        log.info("inside convertToPost()");
        return modelMapper.map(dto, Post.class);
    }

    public PostPreviewDto convertToPostPreviewDto(Post post) {
        log.info("inside convertToPostPreviewDto()");
        return modelMapper.map(post, PostPreviewDto.class);
    }

    public PostDetailsInputDto convertToPostDetailsInputDto(Post post) {
        log.info("inside convertToPostDetailsDto()");
        return modelMapper.map(post, PostDetailsInputDto.class);
    }

    public PostDetailsOutputDto convertToPostDetailsOutputDto(Post post) {
        log.info("inside convertToPostDetailsDto()");
        return modelMapper.map(post, PostDetailsOutputDto.class);
    }
}
