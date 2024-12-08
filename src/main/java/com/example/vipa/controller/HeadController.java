package com.example.vipa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/head")
public class HeadController {

    // Кнопка уведомлений
    @GetMapping("/notifications")
    public String showNotifications() {
        return "notifications";
    }

    // Кнопка подачи нового объявления
    @GetMapping("/newPost")
    public String showNewPost() {
        return "newPost";
    }

    // Меню пользователя: Мои объявления, а также кнопка профиля
    @GetMapping("/myPosts")
    public String showMyPosts() {
        return "myPosts";
    }

    // Меню пользователя: Сообщения
    @GetMapping("/messages")
    public String showMessages() {
        return "messages";
    }

    // Меню пользователя: Настройки
    @GetMapping("/settings")
    public String showSettings() {
        return "settings";
    }

    // Меню пользователя: Избранное
    @GetMapping("/favorites")
    public String showFavorites() {
        return "favorites";
    }

    // Меню пользователя: Корзина
    @GetMapping("/cart")
    public String showCart() {
        return "cart";
    }

    // Меню пользователя: Выход из профиля
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/clients/registration/homepage";
    }

    // Ссылка на главную страницу
    @GetMapping("/homepage")
    public String showHomePage() {
        return "homepage"; // Здесь возвращаем представление главной страницы
    }
}
