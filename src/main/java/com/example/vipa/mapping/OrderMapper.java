package com.example.vipa.mapping;

import com.example.vipa.dto.*;
import com.example.vipa.model.Order;
import com.example.vipa.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        Converter<List<Post>, List<PostPreviewDto>> postListToPostPreviewDtoListConverter =
                src -> src.getSource().stream()
                        .map(post -> modelMapper.map(post, PostPreviewDto.class))
                        .toList();
        Converter<List<Post>, List<String>> postListToPostCoverImageListConverter =
                src -> src.getSource().stream()
                                .map(post -> post.getImages().get(0).getUrl())
                                        .toList();
        modelMapper.createTypeMap(Order.class, OrderDetailsOutputDto.class)
                .addMappings(mapper -> mapper.using(postListToPostPreviewDtoListConverter)
                            .map(Order::getPostsInOrder, OrderDetailsOutputDto::setPostsInOrder));
        modelMapper.createTypeMap(Order.class, OrderPreviewDto.class)
                .addMappings(mapper -> mapper.using(postListToPostCoverImageListConverter)
                        .map(Order::getPostsInOrder, OrderPreviewDto::setPostImages));
    }

    public Order convertToOrder(OrderDetailsInputDto dto) {
        log.info("inside convertToPost()");
        return modelMapper.map(dto, Order.class);
    }

    public OrderPreviewDto convertToOrderPreviewDto(Order order) {
        log.info("inside convertToPostPreviewDto()");
        return modelMapper.map(order, OrderPreviewDto.class);
    }

    public OrderDetailsOutputDto convertToOrderDetailsOutputDto(Order order) {
        log.info("inside convertToPostDetailsDto()");
        return modelMapper.map(order, OrderDetailsOutputDto.class);
    }


}
