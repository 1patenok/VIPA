package com.example.vipa.service;

import com.example.vipa.mapping.PostMapper;
import com.example.vipa.model.Category;
import com.example.vipa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Категория не найдена.";
    public Category getCategory(int categoryId){
        return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND_MESSAGE));
    }
}
