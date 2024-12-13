package com.example.vipa.controller;

import com.example.vipa.dto.ClientDetailsDto;
import com.example.vipa.dto.SignInDto;
import com.example.vipa.model.Client;
import com.example.vipa.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j // для логирования
@Controller // = Component (чтобы автоматически создать объект класса AuthenticationController и поместить его в контекст приложения Spring)
@RequestMapping("/auth") // все запросы, начинающиеся с http://localhost:8080/auth обрабатываются данным контроллером
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class AuthenticationController {

    private final AuthenticationService authService;

    /**
     * Данный метод вызывается, когда нужно получить веб-страницу для входа в аккаунт.
     * @param model - изначально модель пустая, в методе в нее добавляется пустой объект SignInDto,
     *              который будет заполняться в html-форме.
     * @return - возвращаем название представления (html-файла), в котором происходит заполнение формы.
     */
    @GetMapping("/sign-in")
    public String getSignInPage(Model model) {
        log.info("Inside getSignInPage()");
        model.addAttribute("signInDto", new SignInDto());
        return "/auth/sign-in-page";
    }

    /**
     * Данный метод вызывается, когда нужно получить веб-страницу для регистрации пользователя.
     * @param model - изначально модель пустая, в методе в нее добавляется пустой объект ClientDetailsDto,
     *              который будет заполняться в html-форме.
     * @return - возвращаем название представления (html-файла), в котором происходит заполнение формы.
     */
    @GetMapping("/sign-up")
    public String getSignUpPage(Model model) {
        log.info("Inside getSignUpPage()");
        model.addAttribute("clientDetailsDto", new ClientDetailsDto());
        return "/auth/sign-up-page";
    }

    /**
     * Данный метод вызывается, когда на сервер отправляется заполненная форма для входа в аккаунт.
     * @param signInDto - данный аргумент должен поступить из заполненной формы для входа, аннотация
     *                  @ModelAttribute означает, что данный аргумент должен быть получен из
     *                  заполненной html-формы по имени signInDto.
     * @return - редирект на homepage
     */
    @PostMapping("/sign-in")// вход в аккаунт
    public String signIn(@ModelAttribute("signInDto") SignInDto signInDto) {
        log.info("signInDto: {}", signInDto);
        authService.signIn(signInDto);
        return "/common/homepage-client";
    }

    /**
     * Данный метод вызывавется, когда на сервер отправляется заполненная форма для регистрации клиента.
     * @param clientDetailsDto - данный аргумент должен поступить из заполненной формы для регистрации,
     *                         аннотация @ModelAttribute означает, что данный аргумент должен быть получен из
     *                         заполненной html-формы по имени clientDetailsDto.
     * @return - редирект на homepage
     */
    @PostMapping("/sign-up")// регистрация
    public String signUp(@ModelAttribute("clientDetailsDto") ClientDetailsDto clientDetailsDto){
        log.info("clientDetailsDto: {}", clientDetailsDto);
        authService.signUp(clientDetailsDto);
        return "/common/homepage";
    }


}
