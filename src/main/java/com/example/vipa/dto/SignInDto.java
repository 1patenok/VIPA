package com.example.vipa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SignInDto {

    private static final String EMAIL_IS_NOT_VALID_MESSAGE = "Вы ввели невалидный email.";

    @NotBlank(message = "Поле для email не должно быть пустым.")
    @Email(regexp = "^(.+)@(\\S+)$", message = EMAIL_IS_NOT_VALID_MESSAGE)
    private String username;

    @NotBlank(message = "Поле для password не должно быть пустым.")
    private String password;
}