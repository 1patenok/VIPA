package com.example.vipa.service;

import com.example.vipa.dto.CategoryInputDto;
import com.example.vipa.dto.CategoryOutputDto;
import com.example.vipa.exception.NotFoundException;
import com.example.vipa.mapping.CategoryMapper;
import com.example.vipa.model.Category;
import com.example.vipa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Категория не найднеа.";

    private final ImageService imageService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public Category getCategoryEntity(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
    }

    public List<CategoryOutputDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::convertToCategoryOutputDto)
                .toList();
    }

    public void createCategory(CategoryInputDto categoryInputDto) {
        Category categoryToSave = categoryMapper.convertToCategory(categoryInputDto);
        categoryToSave.setImagePath(imageService.saveCategoryImage(categoryInputDto.getImageFile()));
        categoryRepository.save(categoryToSave);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
