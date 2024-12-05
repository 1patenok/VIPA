package com.example.vipa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignInDto {
    @NotNull(message = "Поле для email не должно быть пустым.")
    private String email;
    private String password;
}