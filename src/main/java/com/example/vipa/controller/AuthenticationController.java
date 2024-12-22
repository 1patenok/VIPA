package com.example.vipa.controller;

import com.example.vipa.dto.client.ClientDetailsDto;
import com.example.vipa.dto.auth.SignInDto;
import com.example.vipa.service.AuthenticationService;
import com.example.vipa.validation.ClientValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j // для логирования
@Controller // = Component (чтобы автоматически создать объект класса AuthenticationController и поместить его в контекст приложения Spring)
@RequestMapping("/auth") // все запросы, начинающиеся с http://localhost:8080/auth обрабатываются данным контроллером
@RequiredArgsConstructor // для автоматической генерации конструктора со всеми финальными полями
public class AuthenticationController {

    private final ClientValidator clientValidator;
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
        model.addAttribute("client", new ClientDetailsDto());
        return "/auth/sign-up-page";
    }

    /**
     * Данный метод вызывавется, когда на сервер отправляется заполненная форма для регистрации клиента.
     * @param clientDetailsDto - данный аргумент должен поступить из заполненной формы для регистрации,
     *                         аннотация @ModelAttribute означает, что данный аргумент должен быть получен из
     *                         заполненной html-формы по имени clientDetailsDto.
     * @return - редирект на homepage
     */
    @PostMapping("/sign-up")// регистрация
    public String signUp(Model model,
                         @Valid @ModelAttribute("clientDetailsDto") ClientDetailsDto clientDetailsDto,
                         BindingResult bindingResult) {
        log.info("clientDetailsDto: {}", clientDetailsDto);
        clientValidator.validate(clientDetailsDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Ошибка валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("client", clientDetailsDto);
            return "/auth/sign-up";
        }
        authService.signUp(clientDetailsDto);
        return "/common/homepage-client";
    }


}
