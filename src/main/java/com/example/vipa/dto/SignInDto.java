package com.example.vipa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SignInDto {
    @NotNull(message = "Поле для email не должно быть пустым.")
    private String email;
    @NotNull(message = "Поле для password не должно быть пустым.")
    private String password;
}