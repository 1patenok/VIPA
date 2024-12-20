package com.example.vipa.controller;

import com.example.vipa.dto.CategoryInputDto;
import com.example.vipa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    public final CategoryService categoryService;

    @GetMapping("/new")
    public String getCategoryFormPage(Model model) {
        CategoryInputDto categoryInputDto;
        model.addAttribute("category", new CategoryInputDto());
        return "/category/category-form-page";
    }

    @PostMapping("/new")
    public String createCategory(@ModelAttribute("category") CategoryInputDto categoryInputDto) {
        log.info("Получен запрос на создание новой категории. categoryInputDto: {}", categoryInputDto);
        categoryService.createCategory(categoryInputDto);
        return "redirect:/homepage-client";
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") int categoryId) {
        log.info("Получен запрос на удаление категории. categoryId: {}", categoryId);
        categoryService.deleteCategory(categoryId);
        return "redirect:/homepage-client";
    }
}
