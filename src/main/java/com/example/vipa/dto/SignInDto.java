package com.example.vipa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignInDto {
    private String email;
    private String password;
}