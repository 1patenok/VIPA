package com.example.vipa.mapping;

import com.example.vipa.dto.CategoryInputDto;
import com.example.vipa.dto.CategoryOutputDto;
import com.example.vipa.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public Category convertToCategory(CategoryInputDto dto) {
        return modelMapper.map(dto, Category.class);
    }

    public CategoryOutputDto convertToCategoryOutputDto(Category category) {
        return modelMapper.map(category, CategoryOutputDto.class);
    }

    public CategoryInputDto convertToCategoryInputDto(Category category) {
        return modelMapper.map(category, CategoryInputDto.class);
    }
}
