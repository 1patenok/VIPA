package com.example.vipa.service;

import com.example.vipa.dto.CategoryPreviewDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.CategoryMapper;
import com.example.vipa.model.Category;
import com.example.vipa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Категория не найднеа.";

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public Category getCategoryEntity(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
    }

    public List<CategoryPreviewDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::convertToCategoryPreviewDto)
                .toList();
    }
}
