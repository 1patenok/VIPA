package com.example.vipa.mapping;

import com.example.vipa.dto.CategoryPreviewDto;
import com.example.vipa.model.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryPreviewDto convertToCategoryPreviewDto(Category category) {
        return modelMapper.map(category, CategoryPreviewDto.class);
    }
}
